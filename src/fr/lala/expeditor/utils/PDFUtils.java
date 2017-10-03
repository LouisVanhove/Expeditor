package fr.lala.expeditor.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import fr.lala.expeditor.models.Article;
import fr.lala.expeditor.models.Customer;
import fr.lala.expeditor.models.Order;



public class PDFUtils {
	static Document document ;
	private static final String pdfRepo ="/WEB-INF/pdf/";

	
    public static void createPdf(Order order) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfRepo+"cmd"+order.getId()+".pdf"));
        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        
        setSenderBlock(canvas);
        setReceiverBlock(order.getCustomer(), canvas);
        setOrderInfo(order.getId(), canvas);
        setOrderDetail(order, canvas);

        document.close();
    }
    
	/**
     * Méthode fournissant l'affichage de l'expéditeur
     * @param canvas
     */
    public static void setSenderBlock(PdfContentByte canvas){
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("EXPEDITEUR"), 50, 800, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Notre SA"), 50, 780, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("123 rue du Poney qui Hurle"), 50, 760, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("75000 Paris"), 50, 740, 0);
    }
    
    /**
     * Permet d'ajouter le bloc destinataire au PDF, crée une nouvelle ligne pour chaque String de la liste en paramètres/
     * @param data
     * @param canvas
     */
    public static void setReceiverBlock(Customer customer, PdfContentByte canvas){
    	int i = 800 ;
    	int x = 420 ;
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("DESTINATAIRE"), x, i, 0);
		i = i-20;
    	
    		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(customer.getName()), x, i, 0);
    		i = i-20;
    		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(customer.getAddress()), x, i, 0);
    		i = i-20;
    		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(customer.getZipCode() + " " + customer.getCity()), x, i, 0);
    }
    
    /**
     * Ajoute la ligne n° de commande
     * @param numFacture
     * @param canvas
     */
    private static void setOrderInfo(int numFacture, PdfContentByte canvas) {
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Commande n° "+numFacture), 50, 670, 0);	
    	
    	BarcodeEAN codeEAN = new BarcodeEAN();
    	codeEAN.setCodeType(Barcode.EAN13);
    	codeEAN.setCode("9780201615883");
    	Image imageEAN = codeEAN.createImageWithBarcode(canvas, null, null);
    	imageEAN.setWidthPercentage(5);
    	imageEAN.setAbsolutePosition(350, 670);
    	try {
			document.add(imageEAN);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
              
            try {
				document.add(imageEAN);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
    /**
     * Permet d'afficher la liste des articles dans le tableau
     * @param data
     * @param canvas
     */
    public static void setOrderDetail(Order data, PdfContentByte canvas){
    	int i = 630 ;
    	//En tete
    	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("REF"), 50, i, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("DESIGNATION"), 150, i, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("POIDS"), 350, i, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("QUANTITE"), 480, i, 0);
        i=i-20;
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("__________________________________________________________________________"), 50, i, 0);
        //Génération a partir du tableau : 
        i=i-30;
        for (Article c : data.getListArticles()) {
        	ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getId())), 50, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(c.getLabel()), 150, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getWeight())), 350, i, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(String.valueOf(c.getQuantity())), 480, i, 0);
            i = i-20 ;
		}
        
    }

}
