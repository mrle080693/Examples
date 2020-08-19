$(document).ready(function () {
    $('#modalUpdateBtn').on('show.bs.modal', function (event) {
        var id = $(event.relatedTarget).attr("groupId");
        $("#groupId").val(22);
    });
});
