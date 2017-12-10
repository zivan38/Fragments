package course.labs.fragmentslab;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FriendsFragment extends ListFragment {

	private static final String[] FRIENDS = { "ladygaga", "msrebeccablack",
			"taylorswift13" };
	private static final String TAG = "Lab-Fragments";

	public interface SelectionListener {
		public void onItemSelected(int position);
	}

	private SelectionListener mCallback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// используйте различные определения макета, в зависимости  от вида устройства и является ли устройство pre-
		// или post-honeycomb

		int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1
				: android.R.layout.simple_list_item_1;

		// Устанавливаем адаптер списка для ListFragment
		setListAdapter(new ArrayAdapter<String>(getActivity(), layout, FRIENDS));
	}

		
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Убеждаемся, что хостовое Activity реализовало
		// SelectionListener интерфейс. Нам это необходимо, т.к.
		// когда выбирается элемента списка в ListFragment ,
		// у хвостового Activity вызывается метод onItemSelected().
		
		try {

			mCallback = (SelectionListener) activity;

		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement SelectionListener");
		}
	}

	// Замечание: ListFragments содержит onCreateView() метод по умолчанию.
	// Для других фрагментов, экземпляров класса Fragment вы обычно сами его реализовываете.
	// 	@Override
	//  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	//		Bundle savedInstanceState)

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Log.i(TAG, "Entered onActivityCreated()");

		// При использовании двухпанельного макета, конфигурируем ListView для подсветки
		// выбранного элемента списка
		
		if (isInTwoPaneMode()) {

			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		}

	}

	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {

		// Уведомить хостовой Activity что было произведен выбор.

		mCallback.onItemSelected(position);

	}

	// Если присутствует FeedFragment, значит макет двухпанельный
	private boolean isInTwoPaneMode() {

		return getFragmentManager().findFragmentById(R.id.feed_frag) != null;

	}

}
