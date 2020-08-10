function getGroupTimetable() {
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/timetable/get_group_timetable',
            data: {
                id: $('#groupTimetableForm input[name="id"]').val(),
                from: $('#groupTimetableForm input[name="from"]').val(),
                till: $('#groupTimetableForm input[name="till"]').val()

            },
            success: function (data) {
                var lessons = JSON.parse(data)
                    , body = '';

                for (variable in lessons) {
                    body = body + '<li>' + "id " + lessons[variable].id + "date " + lessons[variable].date +
                        "lessonNumber" + lessons[variable].lessonNumber + "building " + lessons[variable].building +
                        "classroom " + lessons[variable].classroom + '</li>';
                }
                $('#result').html(body);
            }
        });
    });
    return false;
}

function getProfessorTimetable() {
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/timetable/get_professor_timetable',
            data: {
                id: $('#professorTimetableForm input[name="id"]').val(),
                from: $('#professorTimetableForm input[name="from"]').val(),
                till: $('#professorTimetableForm input[name="till"]').val()

            },
            success: function (data) {
                var lessons = JSON.parse(data)
                    , body = '';

                for (variable in lessons) {
                    body = body + '<li>' + "id " + lessons[variable].id + "date " + lessons[variable].date +
                        "lessonNumber" + lessons[variable].lessonNumber + "building " + lessons[variable].building +
                        "classroom " + lessons[variable].classroom + '</li>';
                }
                $('#result').html(body);
            }
        });
    });
    return false;
}
