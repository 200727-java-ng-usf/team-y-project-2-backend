
drop table if exists amg_meal_users; 
drop table if exists amg_meal_prior_restaurants; 
drop table if exists amg_meal_restaurants; 
drop table if exists amg_votes; 
drop table if exists amg_cuisine; 
drop table if exists amg_likes; 
drop table if exists amg_meal; 
drop table if exists amg_users; 

drop table if exists meal_users; 
drop table if exists meal_prior_restaurants; 
drop table if exists meal_restaurants; 
drop table if exists vote; 
drop table if exists cuisine; 
drop table if exists likes; 
drop table if exists meal; 
drop table if exists appuser cascade; 

create table appuser( 
	user_id							serial, 
	username 						varchar(255) not null, 
	password_hash					bytea not null, 
	password_salt					bytea not null, 
	email							varchar(255) unique not null, 
	constraint user_id_pk 
		primary key(user_id) 
); 

-- 
create table meal( 
	meal_id							serial, 
	num_votes 						int default 3, -- determines how many votes each user gets during the voting period 
	invite_code						varchar(255) unique not null, 
	meal_name						varchar(255) not null, 
	constraint meal_id_pk 
		primary key(meal_id) 
); 

-- used to get previously enjoyed restaurants. 
create table likes( 
	amg_like_id						serial, 
	restaurant						varchar(255), 
	user_id 						int, 
	constraint amg_like_id_pk 
		primary key(amg_like_id) 
); 

-- May not be used 
create table cuisine( 
	amg_cuisine_id					serial, 
	cuisine 						varchar(255), 
	constraint amg_cuisine_id_pk 
		primary key(amg_cuisine_id) 
); 

-- used in the meal to vote on which restaurant to go to 
create table vote( 
	amg_vote_id						serial, 
	restaurant						varchar(255), 
	vote_meal_id					int, 
	constraint amg_vote_id_pk 
		primary key(amg_vote_id), 
	constraint vote_meal_fk 
		foreign key (vote_meal_id) 
		references meal (meal_id) 
); 

-- all the restaurants being chosen from any given meal 
create table meal_restaurants( 
	meal_restaurant_id				serial, 
	meal_id 						int, 
	amg_restaurant 					varchar(255), 
	constraint meal_restaurant_id_pk 
		primary key(meal_restaurant_id), 
	constraint meal_restaurants_fk 
		foreign key (meal_id) 
		references meal (meal_id) 
); 

-- all the restaurants that were the final choice from any given meal, used for history 
-- may not be used 
create table restaurants( 
	meal_prior_restaurant_id		serial, 
	meal_id 						int, 
	amg_restaurant 					varchar(255), 
	constraint meal_prior_restaurant_id_pk 
		primary key(meal_prior_restaurant_id), 
	constraint meal_prior_restaurants_fk 
		foreign key (meal_id) 
		references meal (meal_id) 
);

-- Junction table of users and meals: shows which meals have which users.
create table meal_users( 
	meal_id							int, 
	user_id							int, 
	amg_num_user_votes				int,
	constraint meal_users_pk 
		primary key(meal_id, user_id), 
	constraint meal_fk 
		foreign key (meal_id) 
		references meal (meal_id), 
	constraint users_fk 
		foreign key (user_id) 
		references appuser
	 (user_id) 
); 


-- +-------------------------------------------------------------+
-- +                    	  TEST DATA
-- +-------------------------------------------------------------+



-- +-------------------------------------------------------------+
-- +                    	  TESTING
-- +-------------------------------------------------------------+


