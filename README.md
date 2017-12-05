# Android-GoogleMapsAPI

<h3>Description</h3>
<p>Using GoogleMaps API: Distance Matrix, Distance, Duration from Origin to Multiple Destinations are calculated.</p>
<p></p>
<img src="GoogleMapsDistanceMatrix_12_05_17.PNG" style="width:40%">
<p></p>
<h3>Implementation</h3>
<ul>
  <li>Multiple Locations distance/duration requests can be asked at one time to Google Maps.</li>
  <li>In this example, 14 destinations are asked to Google Maps.</li>
  <li>Distance Matrix is part of Google Maps API which outputs distance and duration from origin to destination.</li>
  <li>Data request is made through HttpURLConnection: "GET."</li>
  <li>JSON object is analyzed with Gson. RecyclerView displays destinations vertically. OnClickEvent displays Map.</li>
</ul>

