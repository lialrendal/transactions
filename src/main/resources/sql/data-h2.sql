INSERT INTO interest (`interest_id`,`percentage`, `interest_init_date`, `interest_final_date`) VALUES	
	(0, 0, '1999-12-30', '1999-12-31'),
	(1, 0.6, '2018-10-01','2019-02-01'),
	(2, 0.7, '2019-02-02', '2019-05-01'),
	(3, 0.8, '2019-05-02', '2019-08-01'),
	(4, 0.85, '2019-08-02', '2019-11-01');

INSERT INTO accounts (`account_id`,`client`,`account_type`, `account_amount`, `interest_id`) VALUES
	(3214155629763020, 'Jorge Aguado Rodriguez', 'Ahorro', 1200.5, 0),
	(9256098623205402, 'Lucia Oseira Juarez', 'Ahorro', 32564.20, 0),
	(4575036504889634, 'Felipe Reche Gomez', 'Corriente', 865.93, 0),
	(1256980987324453, 'Felipe Reche Gomez', 'Ahorro', 55000, 0),
	(8820396849064201, 'Luisa Martinez Gonzalez', 'Corriente', 2600, 0),
	(5610235496321084, 'Juan Solves Garcia', 'Corriente', 3689.75, 0),
	(2698530145987410, 'Paula Seoane Lopez', 'Ahorro', 8750, 0),
	(3659812003584090, 'Paula Seoane Lopez', 'Corriente', 1750, 0),
	(2566515002566301, 'Manuel Noya Otero', 'Ahorro', 6570.39, 0),
	(7896204020054361, 'Susana Perez Garcia', 'Corriente', 2143.71, 0),
	(3568009081987066, 'Laura Naya Rios', 'Ahorro', 43628.96, 0),
	(9869700106595821, 'Marta Lopez Mira', 'Corriente', 563.12, 0);

INSERT INTO operations (`operation_id`,`operation_type`,`amount`,`source_account_id`,`destination_account_id`) VALUES
	(1, 'deposit', 50, 0, 7896204020054361),
	(2, 'withdraw', 200, 1256980987324453, 0);