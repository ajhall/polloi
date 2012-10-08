package com.ajhall.polloi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SurveyFinishPage extends Fragment {
	public SurveyFinishPage() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.survey_finish_page, null);

		Button finishButton = (Button) view.findViewById(R.id.finish_button);

		finishButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// @formatter:off
				new AlertDialog.Builder(getActivity())
//				.setTitle("Finish the survey?")
				.setMessage("Are you finished answering this survey?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SurveyDBHelper.writeSurveyAnswers(getActivity());
						SurveyDBHelper.answersToCsv(getActivity(), SurveyDBHelper.getSurveyId(getActivity(), GlobalsApp.survey.getTitle()));
					}
				}).setNegativeButton("No", null)
				.show();
				// @formatter:on
			}
		});

		return view;
	}
}
