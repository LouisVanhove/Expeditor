INSERT INTO PROFILES (label)
     VALUES ('Manager');
INSERT INTO PROFILES (label)
     VALUES ('Préparateur de Commandes');


INSERT INTO EMPLOYEES (login, password, name, firstname, profile, archived)
     VALUES ('jpDurand', '80cdf6ab1d367ce25a437d188a19098b','Durand','Jean-Paul',1, 0);
INSERT INTO EMPLOYEES (login, password, name, firstname, profile, archived)
     VALUES ('pHenri', '80cdf6ab1d367ce25a437d188a19098b','Henri','Patrick',2, 0);
     

INSERT INTO ARTICLES (label,description,weight,archived)
     VALUES ('Disque dur interne 2,5', 'super qualité', 150 ,0);
INSERT INTO ARTICLES (label,description,weight,archived)
     VALUES ('Carte graphique','une résolution haute définition',100 ,0);
INSERT INTO ARTICLES (label,description,weight,archived)
     VALUES ('Carte mère','un vrai chef de projet',200 ,0);
INSERT INTO ARTICLES (label,description,weight,archived)
     VALUES ('Alimentation','conseillé un ordinateur performant',300 ,0);

INSERT INTO STATES VALUES ('PENDING');
INSERT INTO STATES VALUES ('IN_PROGRESS');
INSERT INTO STATES VALUES ('PROCESSED');


