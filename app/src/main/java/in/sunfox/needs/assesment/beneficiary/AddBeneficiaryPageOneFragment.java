package in.sunfox.needs.assesment.beneficiary;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.databinding.FragmentAddBeneficiaryPageOneBinding;
import in.sunfox.needs.assesment.helper.LocalCacheHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBeneficiaryPageOneFragment extends Fragment {

    private TableLayout tableLayout;

    public AddBeneficiaryPageOneFragment() {
        // Required empty public constructor
    }

    @BindingAdapter("members")
    public static void bindMembers(TableLayout tableLayout, List<Beneficiary.FamilyMember> familyMembers) {
        if(familyMembers!=null){
            int i=1;
            for(Beneficiary.FamilyMember familyMember:familyMembers){
                TextView textView = new TextView(tableLayout.getContext());
                textView.setText(String.valueOf(i));

                EditText editText = new EditText(tableLayout.getContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    editText.setTextAppearance(R.style.EditTextGreyBackground);
                }else{
                    editText.setTextAppearance(tableLayout.getContext(),R.style.EditTextGreyBackground);
                }
                editText.setText(familyMember.getName());
                editText.setMaxLines(1);
                editText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        familyMember.setName(editable.toString());
                    }
                });

                Spinner spinner = new Spinner(tableLayout.getContext());
                spinner.setBackground(ContextCompat.getDrawable(tableLayout.getContext(),R.drawable.background_rounded_grey_spinner));
                ArrayAdapter adapter = new ArrayAdapter<String>(tableLayout.getContext(), android.R.layout.simple_list_item_1, tableLayout.getContext().getResources().getStringArray(R.array.employment_array));
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        familyMember.setEmployment((String)adapter.getItem(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                TableRow tableRow = new TableRow(tableLayout.getContext());
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.addView(textView);
                tableRow.addView(editText);
                tableRow.addView(spinner);

                tableLayout.addView(tableRow);
                i++;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        BeneficiaryViewModel viewModel = new ViewModelProvider(requireActivity()).get(BeneficiaryViewModel.class);
        FragmentAddBeneficiaryPageOneBinding beneficiaryPageOneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_beneficiary_page_one, container, false);
        beneficiaryPageOneBinding.setViewModel(viewModel);
        beneficiaryPageOneBinding.setMandalAdapter(new ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,LocalCacheHelper.getMandalsList(requireContext())));
        beneficiaryPageOneBinding.setPinCodesAdapter(new ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,LocalCacheHelper.getPinCodes(requireContext())));
        beneficiaryPageOneBinding.setPanchayatAdapter(new ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,LocalCacheHelper.getPanchayatsList(requireContext())));
        return beneficiaryPageOneBinding.getRoot();
    }


    public void addNewRow(Beneficiary.FamilyMember familyMember){

        Context context = requireContext();
        TextView textView = new TextView(context);
        textView.setText(String.valueOf(tableLayout.getChildCount()+1).concat("."));

        EditText editText = new EditText(tableLayout.getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editText.setTextAppearance(R.style.EditTextGreyBackground);
        }else{
            editText.setTextAppearance(tableLayout.getContext(),R.style.EditTextGreyBackground);
        }
        editText.setHint("Member name");
        editText.setMaxLines(1);
        editText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                familyMember.setName(editable.toString());
            }
        });

        Spinner spinner = new Spinner(tableLayout.getContext());
        ArrayAdapter adapter = new ArrayAdapter<String>(tableLayout.getContext(), android.R.layout.simple_list_item_1, tableLayout.getContext().getResources().getStringArray(R.array.employment_array));
        spinner.setAdapter(adapter);
        spinner.setBackground(ContextCompat.getDrawable(tableLayout.getContext(),R.drawable.background_rounded_grey_spinner));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                familyMember.setEmployment((String)adapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TableRow tableRow = new TableRow(tableLayout.getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.addView(textView);
        tableRow.addView(editText);
        tableRow.addView(spinner);
        tableLayout.addView(tableRow);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         tableLayout = view.findViewById(R.id.table_layout);
    }
}
