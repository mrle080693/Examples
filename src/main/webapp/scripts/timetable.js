function getGroupTimetable() {
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/timetable/get_group_timetable',
            data: {
                groupId: $('#groupTimetableForm input[name="id"]').val(),
                from: $('#groupTimetableForm input[name="from"]').val(),
                till: $('#groupTimetableForm input[name="till"]').val()

            },
            success: function (data) {
                var lessons = data
                    , body = '';

                for (name in lessons) {
                    body = body + '<li>' + "***id: " + lessons[name].id + "***date: " + lessons[name].date +
                        "***lessonNumber: " + lessons[name].lessonNumber + "***building: " + lessons[name].building +
                        "***classroom: " + lessons[name].classroom + '</li>';
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
                professorId: $('#professorTimetableForm input[name="id"]').val(),
                from: $('#professorTimetableForm input[name="from"]').val(),
                till: $('#professorTimetableForm input[name="till"]').val()

            },
            success: function (data) {
                var lessons = data
                    , body = '';

                for (name in lessons) {
                    body = body + '<li>' + "id " + lessons[name].id + "date " + lessons[name].date +
                        "lessonNumber" + lessons[name].lessonNumber + "building " + lessons[name].building +
                        "classroom " + lessons[name].classroom + '</li>';
                }
                $('#result').html(body);
            }
        });
    });
    return false;
}
