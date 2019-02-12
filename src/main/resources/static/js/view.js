function generateSqls() {
    let $tables = $('#tables');
    let selector = $('input:checked');

    let tables = $tables.find(selector).map(function() {
        return $(this).attr('name');
    }).toArray();

    alert(tables);
}