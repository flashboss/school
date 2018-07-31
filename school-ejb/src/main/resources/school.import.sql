-- If you are using Hibernate as the JPA provider, you can use this file to load seed data into the database using SQL statements
-- The portable approach is to use a startup component (such as the @PostConstruct method of a @Startup @Singleton) or observe a lifecycle event fired by Seam Servlet
insert into JBP_PUPILS (id, name, surname, room, school, day, ok) values (1, 'Luca', 'Stancapiano', '3B', 'Maiorana');
insert into JBP_PUPILS (id, name, surname, room, school, day, ok) values (2, 'Alessandro', 'Toro', '2C', 'Cornelio Nepote');