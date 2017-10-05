package fr.lala.expeditor.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class HashageSalagePassword {
	
		/**
	     * Méthode permettant de générer un mot de passe crypté
	     * inclus un salage.
	     * @param password
	     * @return
	     */
		public static String encryptPassword(String password) {
			Logger logger = MonLogger.getLogger("ToolUtilisateur");
			logger.entering("ToolUtilisateur", "encryptPassword");
			
			String salage;
			//Salage mot_de_passe+ les 2 première lettres du mot de passe
			salage = password + password.substring(0, 2);
			
			// Crytage MD5
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				logger.severe("Algorithme de cryptage MD5 non disponible sur l'environnement d'application.");
			}
			md5.update(StandardCharsets.UTF_8.encode(salage));
			logger.exiting("ToolUtilisateur", "encryptPassword");
			 
			return String.format("%032x", new BigInteger(1, md5.digest()));
			
		}
	
	}
