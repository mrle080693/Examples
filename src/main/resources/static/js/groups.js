$('#modalUpdateBtn').on('shown.bs.modal', function (event) {
    var groupId = $(event.relatedTarget).data('group-id');
    var groupName = $(event.relatedTarget).data('group-name');

    $('#group-id').val(groupId);
    $('#group-name').val(groupName);
});
