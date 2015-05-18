package com.example.daoshiclock;

import android.content.Context;
import android.widget.ListView;

import com.example.listener.fanslistlistener;
import com.example.listener.uplistlistener;

public class setlist {
	ListView list;
    Context context;
	public setlist(ListView list,Context context) {

		this.list = list;
		this.context=context;

	}

	public void upclicker() {

		uplistlistener uplistener = new uplistlistener(context);
		list.setOnItemClickListener(uplistener);

	}

	public void fansclicker() {

		fanslistlistener fanlistener = new fanslistlistener(context);
		list.setOnItemClickListener(fanlistener);

	}

}
