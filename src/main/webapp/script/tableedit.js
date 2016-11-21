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
            dataCellObject = $('<td>' + _.escape(row[field.name]) + '</td>');
            if (field.editable) {
                dataCellObject.addClass("editable");
                dataCellObject.data("id", row["id"]);
                dataCellObject.data("column", field.name);
            }
            dataRowObject.append(dataCellObject);
        });
        table.append(dataRowObject);
    });
    $(".editable").one('click', beginEditing);
}

var editBox;
var storedValue;

function beginEditing() {
    if (editBox) stopEditing(false);
    editBox = $(this);
    storedValue = this.innerHTML;
    this.innerHTML = "<input type='text' onblur='stopEditing(false);'>";
    this.firstChild.value = _.unescape(storedValue);
    editBox.on('keyup', function (e) {
        if (e.keyCode === 13) {
            stopEditing(true);
        }
    });
}

function stopEditing(confirmed) {
    if (confirmed) {
        var newValue = editBox[0].firstChild.value;
        editBox.html(_.escape(newValue));
        var dataBlock = "id=" + editBox.data("id")
            + ","
            + editBox.data("column") + "=" + newValue;

        $.ajax({
            url: '/data',
            type: 'PUT',
            data: dataBlock
        });
    } else {
        editBox.html(storedValue);
    }
    editBox.one('click', beginEditing);
}
