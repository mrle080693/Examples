// Get all groups in select elements by default
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/rest/groups',

        success: function (data) {
            var groups = data
                , body = '';

            for (group in groups) {
                var groupId = groups[group].id;
                var groupName = groups[group].name;

                body = body + '<option id="' + groupId + '">' + groupName + '</option>';
            }
            $('#select-group-add').html(body);
            $('#select-group-update').html(body);
        }
    });
});

// Get all professors in select elements by default
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/rest/professors',

        success: function (data) {
            var professors = data
                , body = '';

            for (professor in professors) {
                var professorId = professors[professor].id;
                var professorName = professors[professor].name;

                body = body + '<option id="' + professorId + '">' + professorName + '</option>';
            }
            $('#select-professor-add').html(body);
            $('#select-professor-update').html(body);
        }
    });
});

$('#modalNewBtn').on('shown.bs.modal', function () {
    var selectedGroupId = $('#select-group-add option:selected').attr('id');
    var inputGroupId = $('#group-id-form');
    inputGroupId.val(selectedGroupId);

    var selectedProfessorId = $('#select-professor-add option:selected').attr('id');
    var inputProfessorId = $('#professor-id-form');
    inputProfessorId.val(selectedProfessorId);
});

$('#modalUpdateBtn').on('shown.bs.modal', function (event) {
    var id = $(event.relatedTarget).data('id');
    var date = $(event.relatedTarget).data('date');
    var lessonNumber = $(event.relatedTarget).data('lesson-number');
    var groupId = $(event.relatedTarget).data('group-id');
    var professorId = $(event.relatedTarget).data('professor-id');
    var building = $(event.relatedTarget).data('building');
    var classroom = $(event.relatedTarget).data('classroom');

    $('#id').val(id);
    $('#date').val(date);
    $('#lesson-number').val(lessonNumber);
    $('#group-id-update').val(groupId);
    $('#professor-id-update').val(professorId);
    $('#building').val(building);
    $('#classroom').val(classroom);
});
