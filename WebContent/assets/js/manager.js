function confirmEmployeeSuppression(idform) {
    
    var r = confirm("Voulez-vous vraiment supprimer cet employé ?");
    if (r == true) {
    	document.getElementById(idform).submit();
    }
}