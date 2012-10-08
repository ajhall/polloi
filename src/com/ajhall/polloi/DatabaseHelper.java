package com.ajhall.polloi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Handles creation of and access to the database, which is stored in surveys.db.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "surveys.db";

	private static final String CREATE_TABLE_SURVEYS = "CREATE TABLE surveys (survey_id INTEGER PRIMARY KEY, title TEXT UNIQUE NOT NULL, intro_text TEXT)";
	private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE questions (question_id INTEGER PRIMARY KEY, survey_id INTEGER, section TEXT, question_order INTEGER, type TEXT, prompt TEXT)";
	private static final String CREATE_TABLE_QUESTION_OPTIONS = "CREATE TABLE question_options (question_id INTEGER, option_order INTEGER, answer_text TEXT, text_field_label TEXT)";

	/**
	 * Create a helper object to create, open, and/or manage a database. This method always returns
	 * very quickly. The database is not actually created or opened until one of
	 * getWritableDatabase() or getReadableDatabase() is called.
	 * 
	 * @param context
	 *        To open or create the database
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(CREATE_TABLE_SURVEYS);
			db.execSQL(CREATE_TABLE_QUESTIONS);
			db.execSQL(CREATE_TABLE_QUESTION_OPTIONS);

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

		initializeSurveys(db);
		initializeAnswerTables(db);
//		db.close();	// crashes on startup
	}

	/**
	 * Writes the default set of surveys to the database. Refer to the CREATE_TABLE_* strings in
	 * this class for the structure of the tables. Survey titles must be unique.
	 * 
	 * @param db
	 *        The database to which the surveys will be written.
	 */
	private void initializeSurveys(SQLiteDatabase db) {
		ContentValues cv = new ContentValues();

		// test
		cv.put("survey_id", 1);
		cv.put("title", "Fake survey number one");
		cv.put("intro_text", "Some intro text goes here");
		db.insert("surveys", null, cv);
		
		cv.clear();
		cv.put("survey_id", 1);
		cv.put("question_id", 100);
		cv.put("question_order", 1);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "This question is false");
		db.insert("questions", null, cv);
		
		cv.clear();
		cv.put("question_id", 100);
		cv.put("option_order", 1);
		cv.put("answer_text", "True");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 100);
		cv.put("option_order", 2);
		cv.put("answer_text", "False");
		db.insert("question_options", null, cv);
		cv.clear();
		
		// //////// SURVEYS //////////
		cv.put("survey_id", 0);
		cv.put("title", "Pre-Trip Student Survey");
		cv.put("intro_text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla volutpat placerat molestie. Vivamus vehicula pretium laoreet. Mauris at lectus vel mauris sodales aliquet. Quisque aliquam convallis justo, sit amet varius ipsum mattis et. Proin a eleifend purus. Proin eu augue elit. Aliquam nunc lorem, viverra sed accumsan vitae, placerat eget ante. Fusce at tortor sapien. Nulla pretium, magna sit amet venenatis vestibulum, tellus tellus imperdiet risus, in tincidunt nunc augue sit amet tellus. Proin sollicitudin felis ut felis placerat dictum. In nisi felis, convallis sit amet cursus in, adipiscing vehicula diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n\nNunc sit amet nunc dolor, vitae congue mi. Nullam sit amet diam velit, placerat viverra quam. Donec ut enim tortor, vel dapibus nibh. Etiam lobortis suscipit dui a consequat. Vestibulum sollicitudin tincidunt elementum. Aliquam quis scelerisque tellus. Nullam augue quam, consequat ac porta eu, venenatis vitae purus. Aenean porttitor vestibulum dolor, at laoreet ipsum eleifend in. Cras velit neque, posuere accumsan varius placerat, adipiscing a felis. Praesent sed nisi at metus tincidunt iaculis sit amet ac orci. Proin sollicitudin dictum tincidunt. Nam lacinia ultricies risus eget condimentum. Quisque consectetur scelerisque turpis nec dapibus. Donec cursus accumsan quam, vel tincidunt nibh aliquam vel. Quisque vitae hendrerit lectus. Cras et pulvinar nisi.");
		db.insert("surveys", null, cv);

		// //////// QUESTIONS //////////
		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 1);
		cv.put("question_order", 1);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "My international travel during the last two years has been primarily for:");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 2);
		cv.put("question_order", 2);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "My decision to participate in this International Samaritan trip was PRIMARILY influenced by:");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 3);
		cv.put("question_order", 3);
		cv.put("type", Question.QUESTION_CHECKBOX);
		cv.put("prompt", "In developing countries like Guatemala, El Salvador, and Honduras, I believe that the following factors influence why most people live in poverty: (Please check those that apply)");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 4);
		cv.put("question_order", 4);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Did you do any volunteer service work during the past 12 months?");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 5);
		cv.put("question_order", 5);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Did you volunteer with International Samaritan during the past 12 months?");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 6);
		cv.put("question_order", 6);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "How many total hours of volunteer service did you provide in the past 12 months, totaling up volunteer time for all organizations for which you volunteer?");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 7);
		cv.put("question_order", 7);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "During the past 12 months, did you donate any money or in-kind goods (clothes, blankets, furniture, etc.) to a charitable organization?");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 8);
		cv.put("question_order", 8);
		cv.put("type", Question.QUESTION_CHECKBOX);
		cv.put("prompt", "During the past 12 months, did you make a financial contribution to support any of the following charities? (Please check all that apply)");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 9);
		cv.put("question_order", 9);
		cv.put("type", Question.QUESTION_CHECKBOX);
		cv.put("prompt", "How did you pay for your participation in this International Samaritan trip? (Please check all those that apply)");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 10);
		cv.put("question_order", 10);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "As I begin this trip, my BIGGEST concern/fear is");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 11);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 11);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Doing something for others without expecting any reward");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 12);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 12);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Donating money to help others less fortunate than myself");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 13);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 13);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Being guided by my conscience towards helping the poor");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 14);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 14);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Taking care of myself and my family before helping others");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 15);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 15);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Promoting cooperation between nations");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 16);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 16);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Being compassionate towards those in need");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 17);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 17);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Volunteering my time and talent towards helping the needy");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 18);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 18);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Working towards mutual acceptance and understanding for people from other countries");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 19);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 19);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Dedicating my academic interests/profession towards resolving poverty");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 20);
		cv.put("section", "Please indicate the importance of value you place on the following.");
		cv.put("question_order", 20);
		cv.put("type", Question.QUESTION_MC);
		cv.put("prompt", "Welcoming to the U.S. immigrants seeking a better life");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 21);
		cv.put("question_order", 21);
		cv.put("type", Question.QUESTION_WRITING);
		cv.put("prompt", "What do you think are major causes of severe poverty in developing countries?");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 22);
		cv.put("question_order", 22);
		cv.put("type", Question.QUESTION_WRITING);
		cv.put("prompt", "Why did you decide to participate in a International Samaritan trip to Central America?");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 23);
		cv.put("question_order", 23);
		cv.put("type", Question.QUESTION_WRITING);
		cv.put("prompt", "What most concerns you about the people and places you will encounter during your trip?");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 24);
		cv.put("question_order", 24);
		cv.put("type", Question.QUESTION_WRITING);
		cv.put("prompt", "What is your greatest hope for this trip?");
		db.insert("questions", null, cv);

		cv.clear();
		cv.put("survey_id", 0);
		cv.put("question_id", 25);
		cv.put("question_order", 25);
		cv.put("type", Question.QUESTION_WRITING);
		cv.put("prompt", "What do you believe are the most effective tools available to individuals, organizations, and government entities (local & global) to address severe global poverty?");
		db.insert("questions", null, cv);

		// //////// CHOICES //////////
		cv.clear();
		cv.put("question_id", 1);
		cv.put("option_order", 1);
		cv.put("answer_text", "Vacation");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 1);
		cv.put("option_order", 2);
		cv.put("answer_text", "Business");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 1);
		cv.put("option_order", 3);
		cv.put("answer_text", "Mission work");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 1);
		cv.put("option_order", 4);
		cv.put("answer_text", "Education");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 1);
		cv.put("option_order", 5);
		cv.put("answer_text", "Did not travel internationally");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 2);
		cv.put("option_order", 1);
		cv.put("answer_text", "International Samaritan promotional video / website");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 2);
		cv.put("option_order", 2);
		cv.put("answer_text", "Recommendation of others (e.g. family members / other students)");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 2);
		cv.put("option_order", 3);
		cv.put("answer_text", "Always wanting to do mission work");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 2);
		cv.put("option_order", 4);
		cv.put("answer_text", "Considering this trip as an extension of my educational experience");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 2);
		cv.put("option_order", 5);
		cv.put("answer_text", "Prodding by a friend");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 2);
		cv.put("option_order", 6);
		cv.put("answer_text", "Feeling I need to see International Samaritan programs first hand");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 1);
		cv.put("answer_text", "Lack of personal effort");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 2);
		cv.put("answer_text", "Substance abuse");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 3);
		cv.put("answer_text", "Failed relationships or divorce");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 4);
		cv.put("answer_text", "Lack of education or work skills");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 5);
		cv.put("answer_text", "Being unfaithful to God");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 6);
		cv.put("answer_text", "Complexity of poverty - no easy fix");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 7);
		cv.put("answer_text", "State of the economy/lack of good jobs");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 8);
		cv.put("answer_text", "Poor public policy choices");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 3);
		cv.put("option_order", 9);
		cv.put("answer_text", "None of the above");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 4);
		cv.put("option_order", 1);
		cv.put("answer_text", "Yes");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 4);
		cv.put("option_order", 2);
		cv.put("answer_text", "No (If \"no\", please skip to question 7)");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 5);
		cv.put("option_order", 1);
		cv.put("answer_text", "Yes");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 5);
		cv.put("option_order", 2);
		cv.put("answer_text", "No");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 6);
		cv.put("option_order", 1);
		cv.put("answer_text", "1 - 10 hours");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 6);
		cv.put("option_order", 2);
		cv.put("answer_text", "11 - 20 hours");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 6);
		cv.put("option_order", 3);
		cv.put("answer_text", "21 - 40 hours");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 6);
		cv.put("option_order", 4);
		cv.put("answer_text", "More than 40 hours");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 7);
		cv.put("option_order", 1);
		cv.put("answer_text", "Yes");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 7);
		cv.put("option_order", 2);
		cv.put("answer_text", "No");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 8);
		cv.put("option_order", 1);
		cv.put("answer_text", "United Way");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 8);
		cv.put("option_order", 2);
		cv.put("answer_text", "American Red Cross");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 8);
		cv.put("option_order", 3);
		cv.put("answer_text", "World Vision");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 8);
		cv.put("option_order", 4);
		cv.put("answer_text", "American Cancer Society");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 8);
		cv.put("option_order", 5);
		cv.put("answer_text", "International Samaritan");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 8);
		cv.put("option_order", 6);
		cv.put("answer_text", "The Humane Society");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 8);
		cv.put("option_order", 7);
		cv.put("answer_text", "Did not donate to these charities");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 9);
		cv.put("option_order", 1);
		cv.put("answer_text", "Savings");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 9);
		cv.put("option_order", 2);
		cv.put("answer_text", "Parents");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 9);
		cv.put("option_order", 3);
		cv.put("answer_text", "Monetary gifts (Christmas and birthday)");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 9);
		cv.put("option_order", 4);
		cv.put("answer_text", "Job earnings");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 9);
		cv.put("option_order", 5);
		cv.put("answer_text", "Self");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 9);
		cv.put("option_order", 6);
		cv.put("answer_text", "Fund-raising activities");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 9);
		cv.put("option_order", 7);
		cv.put("answer_text", "Scholarship");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 9);
		cv.put("option_order", 8);
		cv.put("answer_text", "Other");
		cv.put("text_field_label", "Please comment");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 10);
		cv.put("option_order", 1);
		cv.put("answer_text", "Personal safety");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 10);
		cv.put("option_order", 2);
		cv.put("answer_text", "Getting sick");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 10);
		cv.put("option_order", 3);
		cv.put("answer_text", "Not speaking Spanish");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 10);
		cv.put("option_order", 4);
		cv.put("answer_text", "Being overwhelmed by what I will see and experience");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 10);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not feeling prepared");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 10);
		cv.put("option_order", 6);
		cv.put("answer_text", "Standing out as a foreigner");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 10);
		cv.put("option_order", 7);
		cv.put("answer_text", "Airplane problems / losing my luggage");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 10);
		cv.put("option_order", 8);
		cv.put("answer_text", "Other");
		cv.put("text_field_label", "Please explain");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 11);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 11);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 11);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 11);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 11);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 12);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 12);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 12);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 12);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 12);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 13);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 13);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 13);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 13);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 13);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 14);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 14);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 14);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 14);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 14);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 15);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 15);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 15);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 15);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 15);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 16);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 16);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 16);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 16);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 16);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 17);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 17);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 17);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 17);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 17);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 18);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 18);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 18);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 18);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 18);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 19);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 19);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 19);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 19);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 19);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 20);
		cv.put("option_order", 1);
		cv.put("answer_text", "Extremely important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 20);
		cv.put("option_order", 2);
		cv.put("answer_text", "Quite important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 20);
		cv.put("option_order", 3);
		cv.put("answer_text", "Moderately important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 20);
		cv.put("option_order", 4);
		cv.put("answer_text", "Somewhat important");
		db.insert("question_options", null, cv);

		cv.clear();
		cv.put("question_id", 20);
		cv.put("option_order", 5);
		cv.put("answer_text", "Not at all important");
		db.insert("question_options", null, cv);
	}

	/**
	 * Creates tables to hold the results of each survey.
	 * 
	 * @param db
	 *        The database to which the empty answer tables will be written.
	 */
	private void initializeAnswerTables(SQLiteDatabase db) {
		Cursor c = db.query("surveys", new String[] { "count(*)" }, null, null, null, null, null);
		c.moveToFirst();
		int numSurveys = c.getInt(0);
		c.close();

		for(int surveyIterator = 0; surveyIterator < numSurveys; surveyIterator++) {
			initializeAnswerTable(db, surveyIterator);
		}

			
//		db.close();		// can't close DB here because it is still needed during startup sequence
	}
		
	public static void initializeAnswerTable(SQLiteDatabase db, int surveyIndex) {
		Survey survey = SurveyDBHelper.readSurvey(db, surveyIndex);

		/*
		 * Build a table to hold survey answers. Each writing question should have one column to
		 * store its answer. Each multiple choice should have one column per answer, plus one
		 * for each textField associated with an answer option. Checkbox questions should have
		 * one column per check, plus one column for each textField associated with an answer
		 * option. THIS IS SO UGLY
		 */

		String tableName = "answers_" + (surveyIndex);
		String createTable = "CREATE TABLE " + tableName + " (user_id TEXT UNIQUE NOT NULL COLLATE NOCASE, ";

		for(int questionIterator = 0; questionIterator < survey.getSize(); questionIterator++) {
			String className = survey.getQuestion(questionIterator).getClass().getName();

			if(className.endsWith(".QuestionWriting")) {
				// example: "CREATE TABLE answers1 (q1 TEXT,
				createTable += "q" + (questionIterator + 1) + " TEXT, ";
			}

			else if(className.endsWith(".QuestionMC")) {
				// example: "CREATE TABLE answers1 (q1 TEXT, q1_1_text_field TEXT,
				createTable += "q" + (questionIterator + 1) + " TEXT, ";
				for(int choiceIterator = 0; choiceIterator < ((QuestionMC) survey.getQuestion(questionIterator)).getChoiceCount(); choiceIterator++) {
					if(((QuestionMC) survey.getQuestion(questionIterator)).textFieldAtIndex(choiceIterator)) {
						createTable += "q" + (questionIterator + 1) + "_" + (choiceIterator + 1) + "_text_field TEXT, ";
					}
				}
			}

			else if(className.endsWith(".QuestionCheckbox")) {
				// example: "CREATE TABLE answers1 (q1_1 TEXT, q1_1_text_field TEXT
				for(int choiceIterator = 0; choiceIterator < ((QuestionCheckbox) survey.getQuestion(questionIterator)).getChoiceCount(); choiceIterator++) {
					createTable += "q" + (questionIterator + 1) + "_" + (choiceIterator + 1) + " TEXT, ";
					if(((QuestionCheckbox) survey.getQuestion(questionIterator)).textFieldAtIndex(choiceIterator)) {
						createTable += "q" + (questionIterator + 1) + "_" + (choiceIterator + 1) + "_text_field TEXT, ";
					}
				}
			}
		}
		createTable = createTable.substring(0, createTable.length() - 2) + ")";

		// TODO make sure this actually does anything/works
		try {
			db.execSQL(createTable);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
