
drop table if exists amg_meal_users; 
drop table if exists amg_meal_prior_restaurants; 
drop table if exists amg_meal_restaurants; 
drop table if exists amg_votes; 
drop table if exists amg_cuisines; 
drop table if exists amg_likes; 
drop table if exists amg_meal; 
drop table if exists amg_users; 

create table amg_users( 
	amg_user_id						serial, 
	username 						varchar(255) not null, 
	password_hash					bytea not null, 
	password_salt					bytea not null, 
	email							varchar(255) unique not null, 
	constraint amg_user_id_pk 
		primary key(amg_user_id) 
); 

-- 
create table amg_meal( 
	amg_meal_id						serial, 
	num_votes 						int default 3, -- determines how many votes each user gets during the voting period 
	invite_code						varchar(255) unique not null, 
	meal_name						varchar(255) not null, 
	constraint amg_meal_id_pk 
		primary key(amg_meal_id) 
); 

-- used to get previously enjoyed restaurants. 
create table amg_likes( 
	amg_like_id						serial, 
	restaurant						varchar(255), 
	amg_user_id 					int, 
	constraint amg_like_id_pk 
		primary key(amg_like_id) 
); 

-- May not be used 
create table amg_cuisines( 
	amg_cuisine_id					serial, 
	cuisine 						varchar(255), 
	constraint amg_cuisine_id_pk 
		primary key(amg_cuisine_id) 
); 

-- used in the meal to vote on which restaurant to go to 
create table amg_votes( 
	amg_vote_id						serial, 
	restaurant						varchar(255), 
	vote_meal_id					int, 
	constraint amg_vote_id_pk 
		primary key(amg_vote_id), 
	constraint vote_meal_fk 
		foreign key (vote_meal_id) 
		references amg_meal (amg_meal_id) 
); 

-- all the restaurants being chosen from any given meal 
create table amg_meal_restaurants( 
	amg_meal_restaurant_id			serial, 
	amg_meal_id 					int, 
	amg_restaurant 					varchar(255), 
	constraint amg_meal_restaurant_id_pk  
		primary key(amg_meal_restaurant_id), 
	constraint meal_restaurants_fk 
		foreign key (amg_meal_id) 
		references amg_meal (amg_meal_id) 
); 

-- all the restaurants that were the final choice from any given meal, used for history 
-- may not be used 
create table amg_meal_prior_restaurants( 
	amg_meal_prior_restaurant_id	serial, 
	amg_meal_id 					int, 
	amg_restaurant 					varchar(255), 
	constraint amg_meal_prior_restaurant_id_pk 
		primary key(amg_meal_prior_restaurant_id), 
	constraint meal_prior_restaurants_fk 
		foreign key (amg_meal_id) 
		references amg_meal (amg_meal_id) 
);

-- Junction table of users and meals: shows which meals have which users.
create table amg_meal_users( 
	amg_meal_id						int, 
	amg_user_id						int, 
	amg_num_user_votes				int,
	constraint amg_meal_users_pk 
		primary key(amg_meal_id, amg_user_id), 
	constraint meal_fk 
		foreign key (amg_meal_id) 
		references amg_meal (amg_meal_id), 
	constraint users_fk 
		foreign key (amg_user_id) 
		references amg_users (amg_user_id) 
); 


-- +-------------------------------------------------------------+
-- +                    	  TEST DATA
-- +-------------------------------------------------------------+



-- +-------------------------------------------------------------+
-- +                    	  TESTING
-- +-------------------------------------------------------------+


