function getGroupEmployment() {
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/statistics/get_group_employment',
            data: {
                groupId: $('#groupForm input[name="id"]').val(),
                from: $('#groupForm input[name="from"]').val(),
                till: $('#groupForm input[name="till"]').val()

            },
            success: function (data) {
                $('#groupEmployment').text("Result is: " + data);
            }
        });
    });
    return false;
}

function getProfessorEmployment() {
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/statistics/get_professor_employment',
            data: {
                professorId: $('#professorForm input[name="id"]').val(),
                from: $('#professorForm input[name="from"]').val(),
                till: $('#professorForm input[name="till"]').val()

            },
            success: function (data) {
                $('#professorEmployment').text("Result is: " + data);
            }
        });
    });
    return false;
}
