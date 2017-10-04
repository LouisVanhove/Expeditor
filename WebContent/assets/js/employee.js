function getDeliveryNote(formId){
	var r = confirm("Voulez-vous vraiment créer le bon de livraison ?");
    if (r == true) {
    	document.getElementById(formId).submit();
    }
	
}