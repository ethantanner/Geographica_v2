package edu.augustana.csc490.geographica_v2;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 5/10/2015.
 */
public class GetPolygons {

    public GetPolygons(){

    }

    public List<LatLng> getAmericas(){

        List<LatLng> list = new ArrayList<LatLng>();

        list.add(new LatLng(71.226685, -157.675781));
        list.add(new LatLng(70.594371, -161.630859));
        list.add(new LatLng(69.729528, -163.564453));
        list.add(new LatLng(69.080335, -164.003906));
        list.add(new LatLng(68.796068, -166.904297));
        list.add(new LatLng (68.796068, -166.904297));
        list.add(new LatLng(67.114476, -164.003906));
        list.add(new LatLng(66.666036, -164.443359));
        list.add(new LatLng(65.780251, -168.486328));
        list.add(new LatLng(64.411177, -166.289063));
        list.add(new LatLng(64.297058, -161.806641));
        list.add(new LatLng(63.758208, -161.191406));
        list.add(new LatLng(62.689269, -165.410156));
        list.add(new LatLng(61.412494, -166.552734));
        list.add(new LatLng(59.327585, -163.828125));
        list.add(new LatLng(58.510914, -162.158203));
        list.add(new LatLng(58.326799, -157.851563));
        list.add(new LatLng(55.247815, -163.476563));
        list.add(new LatLng(54.743650, -162.773438));
        list.add(new LatLng(57.249338, -155.039063));
        list.add(new LatLng(59.506455, -149.589844));
        list.add(new LatLng(59.817209, -142.822266));
        list.add(new LatLng(58.602611, -138.251953));
        list.add(new LatLng(55.795105, -134.648438));
        list.add(new LatLng(54.386955, -133.066406));
        list.add(new LatLng(48.070738, -124.804688));
        list.add(new LatLng(43.858297, -124.541016));
        list.add(new LatLng(39.393755, -124.365234));
        list.add(new LatLng(34.840859, -121.552734));
        list.add(new LatLng(31.756196, -117.333984));
        list.add(new LatLng(28.410728, -114.433594));
        list.add(new LatLng(22.299261, -110.039063));
        list.add(new LatLng(23.594194, -106.962891));
        list.add(new LatLng(20.086889, -105.908203));
        list.add(new LatLng(15.823966, -98.437500));
        list.add(new LatLng(15.739388, -94.746094));
        list.add(new LatLng(14.040673, -92.197266));
        list.add(new LatLng(12.843938, -88.066406));
        list.add(new LatLng(9.394871, -86.132813));
        list.add(new LatLng(9.394871, -86.132813));
        list.add(new LatLng(7.133598, -80.068359));
        list.add(new LatLng(6.173324, -78.046875));
        list.add(new LatLng(3.634036, -77.958984));
        list.add(new LatLng(0.472407, -80.595703));
        list.add(new LatLng(-4.357366, -81.386719));
        list.add(new LatLng(-14.572951, -76.464844));
        list.add(new LatLng(-15.760536, -75.146484));
        list.add(new LatLng(-18.698285, -70.751953));
        list.add(new LatLng(-29.200123, -71.894531));
        list.add(new LatLng(-33.696923, -72.246094));
        list.add(new LatLng(-45.928230, -75.937500));
        list.add(new LatLng(-51.978113, -75.761719));
        list.add(new LatLng(-56.102683, -69.785156));
        list.add(new LatLng(-55.310391, -64.423828));
        list.add(new LatLng(-54.399748, -64.951172));
        list.add(new LatLng(-53.468431, -67.148438));
        list.add(new LatLng(-50.492463, -67.587891));
        list.add(new LatLng(-47.376035, -64.775391));
        list.add(new LatLng(-43.747289, -64.423828));
        list.add(new LatLng(-39.478606, -59.853516));
        list.add(new LatLng(-37.622934, -55.986328));
        list.add(new LatLng(-31.625321, -50.625000));
        list.add(new LatLng(-27.552112, -47.856445));
        list.add(new LatLng(-23.750154, -44.208984));
        list.add(new LatLng(-23.064570, -41.484375));
        list.add(new LatLng(-19.544261, -39.111328));
        list.add(new LatLng(-13.138328, -38.012695));
        list.add(new LatLng(-8.086423, -34.277344));
        list.add(new LatLng(-4.417614, -34.848633));
        list.add(new LatLng(-2.399811, -41.791992));
        list.add(new LatLng(0.763527, -49.482422));
        list.add(new LatLng(5.194894, -51.152344));
        list.add(new LatLng(6.724620, -56.645508));
        list.add(new LatLng(9.161756, -59.853516));
        list.add(new LatLng(11.323867, -62.138672));
        list.add(new LatLng(11.022080, -67.675781));
        list.add(new LatLng(12.441941, -70.092773));
        list.add(new LatLng(12.699292, -71.982422));
        list.add(new LatLng(11.410033, -74.575195));
        list.add(new LatLng(9.031578, -76.860352));
        list.add(new LatLng(10.158153, -78.750000));
        list.add(new LatLng(9.291886, -81.210938));
        list.add(new LatLng(11.022080, -83.320313));
        list.add(new LatLng(15.427206, -82.880859));
        list.add(new LatLng(16.357039, -87.802734));
        list.add(new LatLng(21.427503, -86.000977));
        list.add(new LatLng(21.672744, -88.769531));
        list.add(new LatLng(21.181851, -90.615234));
        list.add(new LatLng(18.869905, -92.153320));
        list.add(new LatLng(19.533907, -95.932617));
        list.add(new LatLng(23.820526, -97.470703));
        list.add(new LatLng(27.503399, -96.811523));
        list.add(new LatLng(29.358239, -93.515625));
        list.add(new LatLng(29.012944, -91.098633));
        list.add(new LatLng(28.627925, -89.033203));
        list.add(new LatLng(29.969212, -86.923828));
        list.add(new LatLng(30.007274, -86.088867));
        list.add(new LatLng(29.473079, -85.561523));
        list.add(new LatLng(29.243270, -84.726563));
        list.add(new LatLng(28.705043, -83.232422));
        list.add(new LatLng(27.542371, -83.012695));
        list.add(new LatLng(26.563963, -82.397461));
        list.add(new LatLng(25.219851, -81.518555));
        list.add(new LatLng(24.622051, -80.551758));
        list.add(new LatLng(26.051848, -79.453125));
        list.add(new LatLng(30.652090, -80.991211));
        list.add(new LatLng(33.993473, -77.124023));
        list.add(new LatLng(35.187278, -75.190430));
        list.add(new LatLng(37.592472, -75.102539));
        list.add(new LatLng(40.225024, -73.432617));
        list.add(new LatLng(40.925965, -69.785156));
        list.add(new LatLng(42.791370, -69.521484));
        list.add(new LatLng(43.846413, -67.939453));
        list.add(new LatLng(44.162504, -66.840820));
        list.add(new LatLng(43.080925, -65.786133));
        list.add(new LatLng(46.023668, -58.886719));
        list.add(new LatLng(47.379754, -60.688477));
        list.add(new LatLng(46.811339, -63.061523));
        list.add(new LatLng(48.875554, -63.632813));
        list.add(new LatLng(50.774682, -58.271484));
        list.add(new LatLng(52.305120, -55.283203));
        list.add(new LatLng(56.334812, -58.974609));
        list.add(new LatLng(55.745666, -121.113281));
        list.add(new LatLng(65.307240, -133.857422));
        list.add(new LatLng(69.850978, -139.042969));
        list.add(new LatLng(70.970447, -150.205078));

        return list;
    }

}
