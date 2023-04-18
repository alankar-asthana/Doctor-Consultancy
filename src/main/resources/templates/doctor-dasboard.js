$.ajax({
    type: "get",
    url: "/api/doctors/" + email,
    success: function (doctor) {
        // Display doctor details on dashboard
          for (var i = 0; i < doctor.length; i++) {
              var doctor = doctors[i];
              var row = $("<tr>");
              row.append($("<td>").text(doctor.name));
              row.append($("<td>").text(doctor.email));
              row.append($("<td>").text(doctor.password));
              row.append($("<td>").text(doctor.specialty));
              row.append($("<td>").text(doctor.degree));
              row.append($("<td>").text(doctor.licenseNumber));
              row.append($("<td>").text(doctor.address));
              row.append($("<td>").text(doctor.phoneNumber));
              row.append($("<td>").text(doctor.timeSlot));
              $("table tbody").append(row);
            }
    },
    error: function () {
        alert("Error retrieving doctor details.");
    }
});
