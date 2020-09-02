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
                    , body =
                    '<br>' +
                    '<tr>' +
                    '<th class="table-cell">Id</th>' +
                    '<th class="table-cell">Date</th>' +
                    '<th class="table-cell">Lesson Number</th>' +
                    '<th class="table-cell">Group Id</th>' +
                    '<th class="table-cell">Professor Id</th>' +
                    '<th class="table-cell">Building</th>' +
                    '<th class="table-cell">Classroom</th>' +
                    '</tr>';

                body = body + '<tr>';

                for (name in lessons) {
                    body = body
                        + '<td class="table-cell">' + lessons[name].id + '</td>'
                        + '<td class="table-cell">' + lessons[name].date + '</td>'
                        + '<td class="table-cell">' + lessons[name].lessonNumber + '</td>'
                        + '<td class="table-cell">' + lessons[name].groupId + '</td>'
                        + '<td class="table-cell">' + lessons[name].professorId + '</td>'
                        + '<td class="table-cell">' + lessons[name].building + '</td>'
                        + '<td class="table-cell">' + lessons[name].classroom + '</td>';
                }

                body = body + '</tr>';

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
                    , body =
                    '<br>' +
                    '<tr>' +
                    '<th class="table-cell">Id</th>' +
                    '<th class="table-cell">Date</th>' +
                    '<th class="table-cell">Lesson Number</th>' +
                    '<th class="table-cell">Group Id</th>' +
                    '<th class="table-cell">Professor Id</th>' +
                    '<th class="table-cell">Building</th>' +
                    '<th class="table-cell">Classroom</th>' +
                    '</tr>';

                body = body + '<tr>';

                for (name in lessons) {
                    body = body
                        + '<td class="table-cell">' + lessons[name].id + '</td>'
                        + '<td class="table-cell">' + lessons[name].date + '</td>'
                        + '<td class="table-cell">' + lessons[name].lessonNumber + '</td>'
                        + '<td class="table-cell">' + lessons[name].groupId + '</td>'
                        + '<td class="table-cell">' + lessons[name].professorId + '</td>'
                        + '<td class="table-cell">' + lessons[name].building + '</td>'
                        + '<td class="table-cell">' + lessons[name].classroom + '</td>';
                }

                body = body + '</tr>';

                $('#result').html(body);
            }
        });
    });
    return false;
}
