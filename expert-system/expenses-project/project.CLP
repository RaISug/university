(defglobal
	?*READ* = "(read)"
	?*QUESTION_SALIENCE* = 100
)

(deffunction process-expense-type-answer (?type)
		(if (eq ?type ed) then
			(assert (education))
		else (if (eq ?type f) then
			(assert (food))
		else (if (eq ?type jf) then
			(assert (junk-food))
		else (if (eq ?type en) then
			(assert (entertainment))
		else (if (eq ?type hn) then
			(assert (household-needs))
		else (if (eq ?type hc) then
			(assert (health-care))
		else (if (eq ?type b) then
			(assert (bills))
		else (if (eq ?type c) then
			(assert (clothes))))))))))
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
					"'Food (f)', 'Junk Food (jf)', 'Clothes (c)', 'Entertainment (en)', 'Health Care (hc), Education (ed), Household needs (hn), Bills (b)"
					f jf c en hc ed hn b
				)
		)
		
		(printout t "==========================================" crlf)
		
		(process-expense-type-answer ?answer)
		
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
		
		(printout t "==========================================" crlf)
		
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
		
		(printout t "==========================================" crlf)
		
		(assert (expense-usefulness ?answer))
)

(defrule ask-if-the-expense-is-important
		(declare (salience (- ?*QUESTION_SALIENCE* 3)))
	=>
		(bind 
			?answer 
				(ask-question 
					"Is the expense important?" 
					"lexemep"
					"'Yes' (y), 'No' (n)"
					y n
				)
		)
		
		(printout t "==========================================" crlf)
		
		(assert (expense-importance ?answer))
)

(defrule ask-if-the-expense-is-expensive-generally
		(declare (salience (- ?*QUESTION_SALIENCE* 4)))
	=>
		(bind 
			?answer 
				(ask-question 
					"Is it an expensive expense generally?" 
					"lexemep"
					"'Yes' (y), 'No' (n)"
					y n
				)
		)
		
		(printout t "==========================================" crlf)
		
		(assert (expense-is-expensive-in-general ?answer))
)

(defrule ask-for-monthly-income
		(declare (salience (- ?*QUESTION_SALIENCE* 5)))
	=>
		(bind 
			?answer 
				(ask-question 
					"Is it an expensive expense generally?" 
					"numberp"
					"numeric values"
				)
		)
		
		(printout t "==========================================" crlf)
		
		(assert (monthly-income ?answer))
)

; print income data in the console
(deffunction print-input-data (?type ?price ?useful ?important ?expensive ?income)
	(printout t crlf)
		
	(printout t "Expense type was: '" ?type "'" crlf)
	(printout t "Expense price was: '" ?price "'" crlf)
	(printout t "Was expense useful: '" ?useful "'" crlf)
	(printout t "Was important: '" ?important "'" crlf)
	(printout t "Is expensive in general: '" ?expensive "'" crlf)
	(printout t "Monthly income: '" ?income "'" crlf)
	
	(printout t crlf)
	(printout t "==========================================" crlf)
	(printout t crlf)
)

(defrule classify-expense
		(expense-type ?type)
		(expense-price ?price)
		(expense-usefulness ?useful)
		(expense-importance ?important)
		(expense-is-expensive-in-general ?expensive)
		(monthly-income ?income)
	=>
		(if 							; if (type == 'food' && price < 100 && isImportant())
			(and
				(eq ?type f)
				(< ?price 100)
				(eq ?important y)
			)
		then
			(assert (acceptable-expense))
		else 
		(if								; if (type == 'clothes' && isUsuallyExpensive() && (isUseful() || isImportant()))
			(and
				(eq ?type c)
				(eq ?expensive y)
				(or
					(eq ?useful y)
					(eq ?important y)
				)
			)
		then
			(assert (acceptable-expense))
		else
		(if								; if (type == 'entertainment' && (isImportant() || (price < 100 && income > 1000)))
			(and
				(eq ?type en)
				(or
					(eq ?important y) ; emotional importance
					(and
						(< ?price 100)
						(> ?income 1000)
					)
				)
			)
		then
			(assert (acceptable-expense))
		else
		(if								; if (type == 'household-needs' && isUsuallyExpensive() && isUseful() && isImportant())
			(and
				(eq ?type hn)
				(eq ?expensive y)
				(eq ?useful y)
				(eq ?important y)
			)
		then
			(assert (acceptable-expense))
		else
		(if 							; if (type == 'junk-food' && ((price <= 10 && income > 400) || (price > 10 && price < 30 && income > 1000)))
			(and
				(eq ?type jf)
				(or 
					(and
						(<= ?price 10)
						(> ?income 400)
					)
					(and 
						(> ?price 10)
						(< ?price 30)
						(> ?income 1000)
					)
				)
			)
		then
			(assert (acceptable-expense))
		else
			(assert (not-reasonable-expense))
		)))))
)

(defrule reasonable-expense
		(education)
	=>
		(printout t crlf)
		(printout t "Reasonable expense" crlf)
		(printout t crlf)
)

(defrule not-reasonable-expense
		(not-reasonable-expense)
	=>
		(printout t crlf)
		(printout t "Not reasonable expense" crlf)
		(printout t crlf)
)

(defrule urgent-expenses
		(health-care)
	=>
		(printout t crlf)
		(printout t "Urgent expense" crlf)
		(printout t crlf)
)

(defrule expenses-that-cant-be-postponed
		(bills)
	=>
		(printout t crlf)
		(printout t "Expense that can't be postponed" crlf)
		(printout t crlf)
)

(defrule acceptable-expense
		(acceptable-expense)
	=>
		(printout t crlf)
		(printout t "Acceptable expense" crlf)
		(printout t crlf)
)

(defrule other-expense
		(other-expense)
	=>
		(printout t crlf)
		(printout t "Expense that can't be classified" crlf)
		(printout t crlf)
)