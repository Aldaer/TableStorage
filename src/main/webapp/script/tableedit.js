const DATA_FIELDS = [
    {
        name: "id",
        editable: false
    },
    {
        name: "NAME",
        editable: true
    }];
const table = $("#dataTable");

alert("Loading data from server");
$.get("/data", onLoadDataFromServer);

function onLoadDataFromServer(data) {
    $(".data").remove();
    $.each(data, function (i, row) {
        dataRowObject = $('<tr class="data"></tr>');
        DATA_FIELDS.forEach(function (field) {
            dataCellObject = $('<td>' + row[field.name] + '</td>');
            if (field.editable) {
                dataCellObject.addClass("editable");
                dataCellObject.data("id", row["id"]);
                dataCellObject.data("column", field.name);
            }
            dataRowObject.append(dataCellObject);
        });
        table.append(dataRowObject);
    });
    $(".editable").click(beginEditing);
}

function beginEditing() {
    $(this).editable(function (value) {
        var putValue = {
            id: $(this).data("id")
        };
        putValue[$(this).data("column")] = value;

        $.ajax({
            url: '/data',
            type: 'PUT',
            data: putValue
        });
        return value;
    });
}