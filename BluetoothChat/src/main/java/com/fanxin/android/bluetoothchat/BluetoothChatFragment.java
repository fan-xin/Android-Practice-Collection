package com.fanxin.android.bluetoothchat;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BluetoothChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BluetoothChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BluetoothChatFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "BTChatFragment-app";
    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter bluetoothAdapter = null;

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    /*
    * Layout Views
    * */
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;


    private ArrayAdapter<String> mConversationArrayAdapter;

    private StringBuffer mOutStringBuffer;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BluetoothChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BluetoothChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BluetoothChatFragment newInstance(String param1, String param2) {
        BluetoothChatFragment fragment = new BluetoothChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConversationView = (ListView)view.findViewById(R.id.in);
        mOutEditText = (EditText)view.findViewById(R.id.edit_text_out);
        mSendButton = (Button)view.findViewById(R.id.button_send);
    }

    /*
    * 判断蓝牙是否打开
    * */
    @Override
    public void onStart() {
        super.onStart();
        //If BT is not on, 请求打开蓝牙
        if (!bluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
        }else{
            //初始化UI界面
            setupChat();
        }


    }

    /*
    * Set up the UI and background operations for chat
    * */

    private void setupChat() {
        Log.d(TAG,"setupChat()");

        mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.message);

        //设置适配器
        mConversationView.setAdapter(mConversationArrayAdapter);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getView();
                if (view != null){
                    TextView textView = view.findViewById(R.id.edit_text_out);
                    String message = textView.getText().toString();
                    sendMeesage(message);

                }
            }
        });

        mOutStringBuffer = new StringBuffer("");

    }

    private void sendMeesage(String message){
        if (message.length() > 0){
            //获取信息，并且通过服务写入
            byte[] send = message.getBytes();

            //清空输入框
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Get local Bluetooth adapater
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //If the adapter is null, then Bluetooth is not supported
        if (bluetoothAdapter == null) {
            FragmentActivity fragmentActivity = getActivity();
            Toast.makeText(fragmentActivity, "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            fragmentActivity.finish();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bluetooth_chat, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
