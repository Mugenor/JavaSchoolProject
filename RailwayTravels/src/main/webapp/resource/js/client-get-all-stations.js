$(function () {
    $('#stations').DataTable({
        paging: false,
        columns: [
            { data: 'name'}
        ],
        columnDefs: [{
            targets: 1,
            searchable: false,
            orderable: false,
        }],
        bAutoWidth: false
    });
});