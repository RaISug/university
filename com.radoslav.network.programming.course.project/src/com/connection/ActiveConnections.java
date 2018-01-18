package com.connection;

import com.request.Request;

import java.util.List;
import java.util.ArrayList;

public class ActiveConnections {

    private static List<Client> clients = new ArrayList<>();

    public List<Client> getAll() {
        return clients;
    }

    public List<Client> getAllExcept(String identifier) {
        List<Client> result = new ArrayList<>();
        for (Client client : clients) {
            if (identifier.equals(client.identifier())) {
                continue;
            }

            result.add(client);
        }

        return result;
    }

    public void connect(Client client) {
        clients.add(client);
    }

    public void disconnect(Client client) {
        clients.remove(client);
    }

    public Client getClient(String identifier) {
        for (Client client : clients) {
            if (identifier.equals(client.identifier())) {
                return client;
            }
        }

        return null;
    }

    public boolean contains(String value) {
        for (Client client : clients) {
            if (value.equals(client.identifier())) {
                return true;
            }
        }

        return false;
    }
}
