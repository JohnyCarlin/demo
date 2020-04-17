$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/admin/allusers",
        data: '$format=json',
        dataType: 'json',
        success: function (data) {

            var len = data.length;
            for (var i = 0; i < len; i++) {

                var userid = data[i].id;
                var name = data[i].name;
                var lastName = data[i].lastName;
                var email = data[i].email;
                var listroles = data[i].roles;
                var roles = "";
                for (var j = 0; j < listroles.length; j++) {
                    roles += listroles[j].authority + " ";
                }
                $("#users_data").append("<tr><td>" + userid + "</td><td>" + name + "</td><td>" + lastName + "</td><td>" + email + "</td><td>" + roles + "</td><td><button id=" + userid + "  class='btn btn-primary edit'>Edit</button>&nbsp;&nbsp;<button class='delete btn btn-primary' id=" + userid + ">Delete</button></td></tr>");
            }
        }
    });
});

$(document).delegate('.edit', 'click', function () {
    var user_id = this.id;
    $.ajax({
        type: "GET",
        url: "/admin/getuserbyid/" + user_id,
        data: '$format=json',
        dataType: 'json',
        success: function (data) {

            $("#current_user_id").text(user_id);

            var name = data.name;
            var lastName = data.lastName;
            var email = data.email;
            var listroles = data.roles;

            $("#admin_edit").prop('checked', false);
            $("#user_edit").prop('checked', false);
            for (var j = 0; j < listroles.length; j++) {
                if (listroles[j].authority === "ADMIN") {
                    $("#admin_edit").prop('checked', true);
                }
                ;
                if (listroles[j].authority === "USER") {
                    $("#user_edit").prop('checked', true);
                }
                ;
            }

            $("#name_edit").val(name);
            $("#lastname_edit").val(lastName);
            $("#email_edit").val(email);

            $("#editModal").modal();
        }
    })
});

$(document).delegate('#submit_edit_user', 'click', function () {

    var user_id = $("#current_user_id").text();
    var name = $("#name_edit").val();
    var lastName = $("#lastname_edit").val();
    var email = $("#email_edit").val();
    var password = $("#password_edit").val();
    var role_user = "USER";
    var role_admin = "ADMIN";

    var roles_edit = [];
    if ($("#user_edit").is(':checked')) {
        roles_edit.push(role_user);
    }
    if ($("#admin_edit").is(':checked')) {
        roles_edit.push(role_admin);
    }

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/admin/updateuser",
        data: JSON.stringify({
            'id': user_id,
            'name': name,
            'lastName': lastName,
            'email': email,
            'password': password,
            'roles': roles_edit
        }),
        cache: false,
        success: function () {
            $('#editmodal').modal('hide');
            location.reload();
        }
    });
});

$(document).delegate('#submit_new_user', 'click', function () {

    var name = $("#new_user_name").val();
    var lastName = $("#new_user_lastname").val();
    var email = $("#new_user_email").val();
    var password = $("#new_user_password").val();

    var role_user = "USER";
    var role_admin = "ADMIN";

    var roles_new = [];
    if ($("#new_user_role_user").is(':checked')) {
        roles_new.push(role_user);
    }
    if ($("#new_user_role_admin").is(':checked')) {
        roles_new.push(role_admin);
    }

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/admin/insertnewuser",
        data: JSON.stringify({
            'name': name,
            'lastName': lastName,
            'email': email,
            'password': password,
            'roles': roles_new
        }),
        cache: false,
        success: function () {
            location.reload();
        }
    });
});

$(document).delegate('.delete', 'click', function () {
    if (confirm('Do you really want to delete record?')) {
        var user_id = this.id;
        $.ajax({
            type: "DELETE",
            url: "/admin/delete/" + user_id,
            cache: false,
            success: function () {
                location.reload();
            }
        });
    }
});
