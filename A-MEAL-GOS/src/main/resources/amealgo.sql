
drop table if exists amg_votes; 
drop table if exists amg_meal_users; 
drop table if exists amg_meal_restaurants; 
drop table if exists amg_likes; 
drop table if exists amg_meals; 
drop table if exists amg_restaurants; 
drop table if exists amg_users; 

create table amg_users( 
	amg_user_id						serial not null, 
	username 						varchar(255) not null,  -- username is not unique so that users can rename themselves / users can eeach have the same name, e.g., Daniel 
	password_hash					bytea not null, -- password is hashed for security with CSPRNG algorithm in java 
	password_salt					bytea not null, -- password is salted prior to hashing for increased security 
	email							varchar(255) unique not null, 
	constraint user_id_pk 
		primary key(amg_user_id) 
); 

-- all the restaurants that were the final choice from any given meal, used for history 
-- may not be used 
create table amg_restaurants( 
	amg_restaurant_id				serial not null, 
	place_id 						text, -- the Google Maps id for a specific place 
	restaurant_name					text, -- the name of the restaurant 
	maps_link						text, -- the URI of the restaurant's Google Maps page 
	constraint amg_restaurant_id_pk 
		primary key(amg_restaurant_id) 
);
 
-- 
create table amg_meals( 
	amg_meal_id						serial not null, 
	num_votes 						int default 3, -- determines how many votes each user gets during the voting period 
	meal_name						varchar(255) not null, -- user defined name for the meal 
	final_restaurant_id				text, -- the id of the most recent round of voting's restaurant finalist. 
	-- invite_code						varchar(255) unique not null, 
	constraint meal_id_pk 
		primary key(amg_meal_id), 
	constraint final_restaurant_fk 
		foreign key(final_restaurant_id) 
		references amg_restaurants (amg_restaurant_id) 
); 

-- used to get previously enjoyed restaurants. 
create table amg_likes( 
	amg_like_id						serial not null, 
	restaurant_place_id				text not null, -- the Google Maps id for a specific place 
	amg_user_id 					int not null, 
	constraint amg_like_id_pk 
		primary key(amg_like_id), 
	constraint like_user_fk 
		foreign key (amg_user_id) 
		references amg_users (amg_user_id) 
); 

-- all the restaurants being chosen from any given meal 
create table amg_meal_restaurants( 
	amg_restaurant_id					int not null unique, 
	amg_meal_id 						int not null, 
	constraint amg_meal_restaurant_id_pk 
		primary key(amg_restaurant_id, amg_meal_id), 
	constraint restaurant_fk 
		foreign key(amg_restaurant_id) 
		references amg_restaurants (amg_restaurant_id), 
	constraint meal_fk 
		foreign key(amg_meal_id) 
		references amg_meals (amg_meal_id) 
); 

-- Junction table of users and meals: shows which meals have which users. 
create table amg_meal_users( 
	amg_meal_id							int not null, 
	amg_user_id							int not null unique, 
	constraint meal_users_pk 
		primary key(amg_meal_id, amg_user_id), 
	constraint meal_fk 
		foreign key (amg_meal_id) 
		references amg_meals (amg_meal_id), 
	constraint users_fk 
		foreign key (amg_user_id) 
		references amg_users (amg_user_id) 
); 

-- used in the meal to vote on which restaurant to go to 
create table amg_votes( 
	amg_vote_id						serial not null, 
	restaurant_id					int, -- the restaurant being voted for 
	vote_meal_id					int, -- the meal that this vote was in 
	amg_user_id						int, -- the voter id 
	amg_vote						smallint default 0, -- negative = no, 0 = skip, positive = yes 
	constraint amg_vote_id_pk 
		primary key(amg_vote_id), 
	constraint vote_restaurant_fk 
		foreign key (restaurant_id) 
		references amg_restaurants (amg_restaurant_id), 
	constraint vote_meal_fk 
		foreign key (vote_meal_id) 
		references amg_meals (amg_meal_id), 
	constraint vote_user_fk 
		foreign key (amg_user_id) 
		references amg_users (amg_user_id) 
); 

SELECT * FROM appuser;


-- +-------------------------------------------------------------+ 
-- +                    	  TEST DATA 
-- +-------------------------------------------------------------+ 



-- +-------------------------------------------------------------+ 
-- +                    	  TESTING 
-- +-------------------------------------------------------------+ 


