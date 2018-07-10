$(function showAllDepartures() {
    $('#departures').DataTable({
        responsive: true,
        paging: false,
        columnDefs: [{
            targets: 5,
            searchable: false,
            orderable: false,
        }],
        bAutoWidth: false
    });
});