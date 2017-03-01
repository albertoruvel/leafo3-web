var map = null;

function getLeafs() {
    var leafsUrl = 'rest/leafs/';
    $.ajax({
        accept: "application/json",
        url: leafsUrl,
        success: function (response) {
            //iterate over all leafs 
            processLeafsResponse(response);
        },
        error: function (error) {

        }
    });
}

function processLeafsResponse(response) {
    var marker = null;
    $.each(response, function (k, v) {
        var array = v.location.split(',');
        var position = {
            lat: parseFloat(array[0]),
            lng: parseFloat(array[1])
        };
        var icon = {
            //url: 'rest/leafs/leafImage?leafId=' + v.id + '&imageType=0', // url
            url: 'resources/img/leaf.png',
            scaledSize: new google.maps.Size(35, 35), // scaled size
            origin: new google.maps.Point(0, 0), // origin
            anchor: new google.maps.Point(0, 0) // anchor
        };
        //create a new marker 
        marker = new google.maps.Marker({
            map: map,
            draggable: false,
            animation: google.maps.Animation.DROP,
            position: position,
            title: v.title,
            icon: icon
        });
        attachSecretMessage(marker, v.title);
    });
}

function attachSecretMessage(marker, title) {
    var infowindow = new google.maps.InfoWindow({
        content: title
    });

    marker.addListener('click', function () {
        infowindow.open(marker.get('map'), marker);
    });
}

function toggleBounce(marker) {
    if (marker.getAnimation() !== null) {
        marker.setAnimation(null);
    } else {
        marker.setAnimation(google.maps.Animation.BOUNCE);
    }
}


$(window).resize(function () {
    google.maps.event.trigger(map, "resize");
});

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    }
}

function showPosition(position) {
    initMap(position);
}

function initMap(position) {
    var uluru = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
    };
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 2,
        center: uluru
    });
    var marker = new google.maps.Marker({
        position: uluru,
        map: map
    });
    getLeafs();
}
