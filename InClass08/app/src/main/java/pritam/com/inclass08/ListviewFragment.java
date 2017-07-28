package pritam.com.inclass08;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ListviewFragment extends Fragment implements AppInterface{

    private OnFragmentInteractionListener mListener;
    List<App> allAppList;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener.onFragmentSwitch("APP LIST");
        //getActivity().getActionBar().setTitle("APP List");
        //getActivity().findViewById(R.id.listview)
    }

    public ListviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_listview, container, false);
        new GetRssFeed(this).execute("http://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(App app) {
        if (mListener != null) {
            mListener.onPreviewItemClick(app);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void endActivity(ArrayList<App> apps) {
        allAppList=apps;
        Log.d("ListViewFragment",apps.get(0).toString());
        ListView appList = (ListView) getActivity().findViewById(R.id.listview);
        AppListAdapter adapter = new AppListAdapter(getActivity(),R.layout.list_view,apps);
        appList.setAdapter(adapter);
        appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(AppActivity.this,AppPreview.class);
                intent.putExtra("APP",apps.get(position));

                startActivity(intent);*/
                       mListener.onPreviewItemClick(allAppList.get(position));

                   }

               });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPreviewItemClick(App app);
        void onFragmentSwitch(String title);
    }
}
