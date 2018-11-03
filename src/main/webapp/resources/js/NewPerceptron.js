// формируем новые поля
jQuery('.plus').click(function(){
	jQuery('.new_perceptron').before(
	'<tr>' +
		'<td></td>'+
		'<td><select атрибуты class="form-control" id="func[]" placeholder="Активационная функция"><option Функция1>Ф1</option><option функция2>Ф2</option></select></td>'+
		'<td><input type="number" class="form-control" id="neurons[]" placeholder="Количество нейронов"></td>' +
		'<td><span class="btn btn-danger minus pull-right">&ndash;</span></td>' +
	'</tr>'
	);
});
// on - так как элемент динамически создан и обычный обработчик с ним не работает
jQuery(document).on('click', '.minus', function(){
	jQuery( this ).closest( 'tr' ).remove(); // удаление строки с полями
});// JavaScript Document