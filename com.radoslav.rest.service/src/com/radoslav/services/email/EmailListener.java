package com.radoslav.services.email;

import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import com.radoslav.entity.holder.AdminData;
import com.radoslav.entity.holder.Entities;
import com.radoslav.entity.holder.UserData;
import com.radoslav.services.connection.PersistenceManagerUtil;
import com.radoslav.services.email.exception.EmptyMessageException;
import com.radoslav.services.email.exception.UnsupportedDataException;
import com.sun.mail.imap.IMAPFolder;

public class EmailListener {

	private Properties properties = null;
	private String user = null;
	private String password = null;

	public EmailListener(String host, String port, String protocol,
			String password, String user) {
		this.prepareProperties(host, port, protocol);
		this.password = password;
		this.user = user;
	}

	private void prepareProperties(String host, String port, String protocol) {
		this.properties = new Properties();
		this.properties.put("mail.imaps.host", host);
		this.properties.put("mail.imaps.port", port);
		this.properties.put("mail.store.protocol", protocol);
	}

	public void start() {
		new Timer().scheduleAtFixedRate(new DelayedTimerTask(), 5 * 1000,
				30 * 1000);
	}

	private class DelayedTimerTask extends TimerTask {

		@Override
		public void run() {
			Store store = null;
			IMAPFolder folder = null;
			try {
				store = getStore();

				folder = (IMAPFolder) connectToFolder(store, "INBOX");
				
				viewMessageInFolder(folder);
			} catch (NoSuchProviderException noSuchProvider) {
				System.out
						.println("NoSuchProviderException in run() method of MyTimerTask class "
								+ noSuchProvider);
			} catch (MessagingException messageException) {
				System.out
						.println("MessageingException in run() method of MyTimerTask class "
								+ messageException);
			} catch (IOException ioException) {
				System.out
						.println("IOException in run() method of MyTimerTask class "
								+ ioException);
			} finally {
				try {
					if (folder != null) {
						folder.close(false);
					}
					if (store != null) {
						store.close();
					}
				} catch (MessagingException messageException) {
					System.out
							.println("MessageingException in run() method of MyTimerTask class "
									+ messageException);
				}
			}
		}

		private Store getStore() throws NoSuchProviderException {
			return Session.getDefaultInstance(getProperties()).getStore();
		}
		
		private Properties getProperties() {
			return properties;
		}
		
		private Folder connectToFolder(Store store, String folderName) throws MessagingException {
			store.connect(user, password);
			return store.getFolder(folderName);
		}
		
		private void viewMessageInFolder(Folder folder) throws MessagingException, IOException {
			folder.open(Folder.READ_WRITE);
			viewMessages(folder);
		}

		private void viewMessages(Folder folder) throws MessagingException,
				IOException {
			for (Message message : folder.getMessages()) {
				if (isNewMessage(message)) {
					String from = getSenderName(message);
					if (isRightMessage(from)) {
						String subject = getSubject(message);
						readContent(message, subject);
						markMessageAsSeen(message, folder);
					}
				}
			}
		}

		private boolean isNewMessage(Message message) throws MessagingException {
			return message.isSet(Flags.Flag.SEEN) == false;
		}

		private String getSenderName(Message message) throws MessagingException {
			return message.getFrom()[0].toString();
		}
		
		private String getSubject(Message message) throws MessagingException {
			return message.getSubject();
		}
		
		private boolean isRightMessage(String from) {
			return from.contains("radoslav.i.sugarev@gmail.com");
		}

		private void readContent(Part message, String subject) throws MessagingException,
				IOException {
			if (isPlainTextMessage(message)) {
				persistEntity((String) message.getContent(), subject);
			} else if (isMultipartMessage(message)) {
				Multipart multiPart = (Multipart) message.getContent();
				for (int i = 0 ; i < multiPart.getCount() ; i++) {
					readContent(multiPart.getBodyPart(i), subject);
				}
			}
		}
		
		private boolean isPlainTextMessage(Part message) throws MessagingException {
			return message.isMimeType("text/plain");
		}
		
		private boolean isMultipartMessage(Part message) throws MessagingException {
			return message.isMimeType("multipart/*");
		}
		
		private void persistEntity(String jsonString, String subject) {
			if (isInvalidJsonString(jsonString)) {
				throw new EmptyMessageException();
			}
			
			if (subject.equals("User")) {
				PersistenceManagerUtil.persistEntity(Entities.User, jsonString, UserData.class);
			} else if (subject.equals("Admin")) {
				PersistenceManagerUtil.persistEntity(Entities.Admin, jsonString, AdminData.class);
			} else {
				throw new UnsupportedDataException();
			}
		}

		private boolean isInvalidJsonString(String json) {
			return json == null ? true : json.length() == 0 ? true : false;
		}
		
		private void markMessageAsSeen(Message message, Folder folder)
				throws MessagingException {
			folder.setFlags(new Message[] { message }, new Flags(
					Flags.Flag.DELETED), true);
			folder.expunge();
		}
	}
}
