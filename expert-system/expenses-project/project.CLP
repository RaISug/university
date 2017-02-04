(defglobal
	?*READ* = "(read)"
	?*QUESTION_SALIENCE* = 100
)


(deffunction concat (?predicate-function ?answer)

	; concat strings
	; Example of construction (numberp 5)
	
	(bind ?eval (str-cat "(" ?predicate-function))
	(bind ?eval (str-cat ?eval " "))
	(bind ?eval (str-cat ?eval ?answer))
	(bind ?eval (str-cat ?eval ")"))
	
	; return value
	
	?eval
)

(deffunction ask-question (?question ?predicate-function ?allowed-answers-as-text $?allowed-answers)
	
	; ask the question
	
	(printout t crlf)
	(printout t crlf)
	(printout t "[QUESTION] : " ?question crlf)
	(printout t "[ALLOWED ANSWERS] : " ?allowed-answers-as-text crlf)
	(printout t crlf)
	(printout t "[YOUR ANSWER] : ")
	
	(bind ?answer (read))
	
	(if 
		(not 
			(eval 
				(concat ?predicate-function ?answer)
			)
		)
	then
		(printout t "This type of answer is not allowed!" crlf)
		(bind ?answer (ask-question ?question ?predicate-function ?allowed-answers-as-text ?allowed-answers))
	)
	
	(if
		(eq ?predicate-function "lexemep") 
	then
		(bind ?answer (lowcase ?answer))
		(while 
			(not 
				(member ?answer ?allowed-answers)
			) 
		do
			(bind ?answer (ask-question ?question ?predicate-function ?allowed-answers-as-text ?allowed-answers))
		)
	)
	
	; return the answer
	
	?answer
)

(defrule ask-for-the-type-of-the-expense
		(declare (salience ?*QUESTION_SALIENCE*))
	=>
		(bind 
			?answer 
				(ask-question
					"What was the type of the expense?"
					"lexemep"
					"'Food (f)', 'Junk Food (jf)', 'Clothes (c)', 'Entertainment (e)', 'Health Care (hc)"
					f jf c e hc
				)
		)
		
		(assert (expense-type ?answer))
)

(defrule ask-for-the-price-of-the-expense
		(declare (salience (- ?*QUESTION_SALIENCE* 1)))
	=>
		(bind 
			?answer 
				(ask-question 
					"What was the price of the expense?" 
					"numberp"
					"numeric values"
				)
		)
		
		(assert (expense-price ?answer))
)

(defrule ask-if-the-expense-is-useful-in-a-long-term
		(declare (salience (- ?*QUESTION_SALIENCE* 2)))
	=>
		(bind 
			?answer 
				(ask-question 
					"Is the expense useful in a long term?" 
					"lexemep"
					"'Yes' (y), 'No' (n)"
					y n
				)
		)
		
		(assert (expense-usefulness ?answer))
)

; print in the console options

(defrule evaluate-when-price-was-entered
		(expense-type ?type)
		(expense-price ?price)
		(expense-usefulness ?useful)
	=>
		(printout t "Expense type was: " ?type ". Expense price was: " ?price ". Was expense useful: '" ?useful "'" crlf)
)