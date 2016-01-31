$( document ).ready(function(){
    var name = $('#apartmentsSelect option:selected').val();
    $("p").hide();
    $("p[name="+name+"]").show();
});
function onApartmentChange(e){
    var name = $('#'+ e.id +' option:selected').val();
    $("p").hide();
    $("p[name="+name+ e.id.substring(16)+"]").show();
}