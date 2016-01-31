$( document ).ready(function(){
    var user = $('#usersSelect option:selected').val();
    var fields = user.split(" ");
    $('input[name=login]').val(fields[0]);
    $('input[name=password]').val(fields[1]);
    $('input[name=phone]').val(fields[2]);
    $('input[name=email]').val(fields[3]);
    $('option[name='+fields[4].toLowerCase()+']').prop( "selected", true );
    if (fields[5]==='true'){
        $('input[name=isBlocked]').prop( "checked", true );
    }else{
        $('input[name=isBlocked]').prop( "checked", false );
    }
    $('input[name=id]').val(fields[6]);
});
function loadFields(){
    var user = $('#usersSelect option:selected').val();
    var fields = user.split(" ");
    $('input[name=login]').val(fields[0]);
    $('input[name=password]').val(fields[1]);
    $('input[name=phone]').val(fields[2]);
    $('input[name=email]').val(fields[3]);
    $('option[name='+fields[4].toLowerCase()+']').prop( "selected", true );
    if (fields[5]==='true'){
        $('input[name=isBlocked]').prop( "checked", true );
    }
    else{
        $('input[name=isBlocked]').prop( "checked", false );
    }
    $('input[name=id]').val(fields[6]);
}