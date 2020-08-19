// Get all groups in select elements by default
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/groups/get_all',

        success: function (data) {
            var groups = data
                , body = '';

            for (group in groups) {
                body = body + '<option>' + groups[group].name + '</option>';
            }
            $('#selectGroupAdd').html(body);
            $('#selectGroupUpdate').html(body);
        }
    });
});

// Get all professors in select elements by default
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/professors/get_all',

        success: function (data) {
            var professors = data
                , body = '';

            for (professor in professors) {
                body = body + '<option>' + professors[professor].name + '</option>';
            }
            $('#selectProfessorAdd').html(body);
            $('#selectProfessorUpdate').html(body);
        }
    });
});
