INSERT INTO PROFILES (label)
     VALUES ('Manager');
INSERT INTO PROFILES (label)
     VALUES ('Pr�parateur de Commandes');


INSERT INTO EMPLOYEES (login, password, name, firstname, profile, archived)
     VALUES ('jpDurand', '80cdf6ab1d367ce25a437d188a19098b','Durand','Jean-Paul',1, 0);
INSERT INTO EMPLOYEES (login, password, name, firstname, profile, archived)
     VALUES ('pHenri', '80cdf6ab1d367ce25a437d188a19098b','Henri','Patrick',2, 0);
     

INSERT INTO ARTICLES (label,description,weight,archived)
     VALUES ('Disque dur interne 2,5', 'super qualit�', 150 ,0);
INSERT INTO ARTICLES (label,description,weight,archived)
     VALUES ('Carte graphique','une r�solution haute d�finition',100 ,0);
INSERT INTO ARTICLES (label,description,weight,archived)
     VALUES ('Carte m�re','un vrai chef de projet',200 ,0);
INSERT INTO ARTICLES (label,description,weight,archived)
     VALUES ('Alimentation','conseill� un ordinateur performant',300 ,0);

INSERT INTO STATES VALUES ('PENDING');
INSERT INTO STATES VALUES ('IN_PROGRESS');
INSERT INTO STATES VALUES ('PROCESSED');


