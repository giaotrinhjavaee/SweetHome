
//zoom level cua google=vbd+5
var map = null;
var sPrefixMap = "MainContent_ctl00_MapVBD1";
var chuoiTimDiaChi = "";
var Marker = null;
var marker = null;
var pImgMar = "http://muabannhadat.com.vn/images/home_map.gif";
var pImgMarShadow = "http://www.google.com/mapfiles/shadow50.png"
var lvGlo = "10";
var latGlo = "";
var lngGlo = "";
var _ptype = "";
//da + them 5  



var piType = "";
var piLat = "";
var piLng = "";
var piLv = "";


function loadMapCp(type, lat, lng, lv) {
    //	sPrefixMap="MapVBD1";	
    _ptype = type;
    lvGlo = lv;
    latGlo = lat;
    lngGlo = lng;

    var mapOption = {
        scaleControl: true,
        streetViewControl: true,
        overviewMapControl: true,
        overviewMapControlOptions: {opened: true},
        mapTypeControl: true,
        disableDefaultUI: false,
        disableDoubleClickZoom: true,
        scrollwheel: false,
        center: new google.maps.LatLng(10.777494, 106.700320),
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById('gmap'), mapOption);
    google.maps.event.addListener(map, 'click', function (mouseEvent) {
        if (Marker != null)
            Marker.setMap(null);
        var latlng = mouseEvent.latLng;
        if (latlng == null)
            return;
        var icon = NewIcon(pImgMar, pImgMarShadow);

        Marker = new google.maps.Marker({
            position: latlng,
            icon: icon,
            map: map,
            draggable: false
        });
        Marker.setDraggable(false);
        document.getElementById(sPrefixMap + '_txtLatLng').value = latlng.lat() + "," + latlng.lng();
        document.getElementById(sPrefixMap + '_hdfZoom').value = map.getZoom();
        google.maps.event.addListener(Marker, 'dragend', function () {
            document.getElementById(sPrefixMap + '_txtLatLng').value = Marker.getPosition().lat() + "," + Marker.getPosition().lng();
            document.getElementById(sPrefixMap + '_hdfZoom').value = map.getZoom();
        });
    });
    google.maps.event.addListener(map, 'zoomend', function () {
        document.getElementById(sPrefixMap + '_hdfZoom').value = map.getZoom();
        NvgQuiHoachMap.check4GocChoTile(map);
    });

    if ((lat == "0" && lng == "0") || (lat == "" && lng == "") || (lat == "NaN" && lng == "NaN")) {
        if (document.getElementById(sPrefixMap + '_hdfDiaChi').value != "") {
            var geocoder = new google.maps.Geocoder();
            // cl.getLatLng(document.getElementById('text').value.split('!')[0], returnLatLngEstate);

            geocoder.geocode({'address': document.getElementById('text').value.split('!')[0]}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (Marker != null)
                        Marker.setMap(null);
                    map.setCenter(results[0].geometry.location);
                    Marker = new google.maps.Marker({
                        map: map,
                        icon: NewIcon(pImgMar, pImgMarShadow),
                        position: results[0].geometry.location,
                        draggable: true
                    });
                    Marker.setDraggable(true);
                }
                else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {
                    LayMacDinhDQHTP();
                    return;
                }
                else {
                    alert('Geocode was not successful for the following reason: ' + status);
                }
            });

        }
        else
            SetCenterMap(10.7764504, 106.694026, 9);
        return;
    }
    map.setCenter(new google.maps.LatLng(parseFloat(lat), parseFloat(lng)), parseInt(lv));

    var icon_ = NewIcon(pImgMar, pImgMarShadow);
    Marker = new google.maps.Marker({
        position: new google.maps.LatLng(parseFloat(lat), parseFloat(lng)),
        icon: icon_,
        map: map,
        draggable: true
    });
    Marker.setDraggable(true);
    /*	 ***** them marker khi click tren map *****    */
    google.maps.event.addListener(Marker, 'dragend', function () {
        document.getElementById(sPrefixMap + '_txtLatLng').value = Marker.getPosition().lat() + "," + Marker.getPosition().lng();
        document.getElementById(sPrefixMap + '_hdfZoom').value = map.getZoom();
        NvgQuiHoachMap.check4GocChoTile(map);
    });

    google.maps.event.addListener(map, "bounds_changed", function () {
        NvgQuiHoachMap.check4GocChoTile(map);
    });

    /* **** end ****  */
}

function NewIcon(pImgMar, pImgMarShadow) {
    var icon = {url: pImgMar, size: new google.maps.Size(28, 28)};
    if (pImgMarShadow != "")
        icon.shadow = pImgMarShadow;
    return icon;
}

function LayLatLng1() {
    if (map != null) {
        if (document.getElementById('frm:findaddress').value != "") {
            var geocoder = new google.maps.Geocoder();
            // cl.getLatLng(document.getElementById('text').value.split('!')[0], returnLatLngEstate);

            geocoder.geocode({'address': document.getElementById('frm:findaddress').value.split('!')[0]}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (Marker != null)
                        Marker.setMap(null);
                    map.setCenter(results[0].geometry.location);
                    var localtion = results[0].geometry.location + "";
                    localtion = localtion.replace("(", "");
                    localtion = localtion.replace(")", "");
                    document.getElementById('frm:resultgmap').value = localtion;

                    Marker = new google.maps.Marker({
                        map: map,
                        position: results[0].geometry.location,
                        draggable: false
                    });
                    Marker.setDraggable(false);
                }
                else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {
                    return;
                }
                else {
                    alert('Geocode was not successful for the following reason: ' + status);
                }
            });
        }
    }
}

function SetCenterMap(lat, lng, ___lv) {
    map.setCenter(new google.maps.LatLng(lat, lng), ___lv);
}

function SetMap(lat, lng, ___lv) {
    map.setCenter(new google.maps.LatLng(lat, lng), ___lv);
    var myLatlng = new google.maps.LatLng(lat, lng);
    Marker = new google.maps.Marker({
        map: map,
        position: myLatlng,
        draggable: false
    });
    Marker.setDraggable(false);
}

function DefaultMap() {
    SetCenterMap(latGlo, lngGlo, lvGlo);
}
