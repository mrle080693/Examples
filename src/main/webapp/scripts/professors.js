$('#modalUpdateBtn').on('shown.bs.modal', function (event) {
    var id = $(event.relatedTarget).data('id');
    var name = $(event.relatedTarget).data('name');
    var surname = $(event.relatedTarget).data('surname');
    var patronymic = $(event.relatedTarget).data('patronymic');
    var subject = $(event.relatedTarget).data('subject');

    $('#id').val(id);
    $('#name').val(name);
    $('#surname').val(surname);
    $('#patronymic').val(patronymic);
    $('#subject').val(subject);
});
