
function setArticleLineState(Id, qty){
	console.log('Article  '+Id);
	var line = document.getElementById('line_'+Id);
	var icon = document.getElementById('icon_'+Id);
	var input = document.getElementById('article_'+Id);
	var validatedLinesNb = 0; 
	var tabValidatedLines = document.getElementsByTagName('tr');
	var nbLines = tabValidatedLines.length -1 ;
	var btnDeliveryNote = document.getElementById('deliveryBtn');
	console.log('Nombre de lignes trouvées TR : '+nbLines);
	
	
	
	if ( line.classList.contains('completed') )
		document.getElementById("MyElement").classList.toggle('MyClass');
	
	if(qty == input.value){
		line.classList.remove('uncompleted-line');
		line.classList.add('completed-line');
		icon.classList.remove('glyphicon-remove');
		icon.classList.add('glyphicon-ok');
		validatedLinesNb+1;
		
	}else{
		line.classList.remove('completed-line');
		line.classList.add('uncompleted-line');
		icon.classList.remove('glyphicon-ok');
		icon.classList.add('glyphicon-remove');
		
	}
	
	for (var i = 1; i <= nbLines; i++) {
		if(tabValidatedLines[i].classList.contains('completed-line')){
			validatedLinesNb++ ;
		}		
	}
	
	console.log('Nombre de lignes valides : '+validatedLinesNb);
	
	if(validatedLinesNb == nbLines){
		btnDeliveryNote.disabled=false ;
	}else{
		btnDeliveryNote.disabled=true ;
	}
	
	
	
}


function goDeliveryNote(url){
	console.log(url);
	var win = window.open(url, '_blank');
	document.getElementById('nextOrder').disabled = false ;
}

