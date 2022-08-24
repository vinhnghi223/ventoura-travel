package com.Mindelo.Ventoura.UI.View;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.RelativeLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;

import java.util.List;
import java.util.ArrayList;

import com.Mindelo.Ventoura.UI.Activity.R;

/**
 * QuickAction dialog, shows action list as icon and text like the one in
 * Gallery3D app. Currently supports vertical and horizontal layout.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 * 
 *         Contributors: - Kevin Peck <kevinwpeck@gmail.com>
 */
public class TopPopupMenuAction extends PopupWindows implements OnDismissListener {
	private View mRootView;
	private LayoutInflater mInflater;
	private ViewGroup mTrack;
	private ScrollView mScroller;
	private OnActionItemClickListener mItemClickListener;
	private OnDismissListener mDismissListener;

	private List<PopupActionItem> actionItems = new ArrayList<PopupActionItem>();

	private boolean mDidAction;

	private int mChildPos;
	private int mInsertPos;
	private int rootWidth = 0;

	/**
	 * Constructor for default vertical layout
	 * 
	 * @param context
	 *            Context
	 */
	public TopPopupMenuAction(Context context) {
		super(context);

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		setRootViewId(R.layout.popup_vertical);

		mChildPos = 0;
	}

	/**
	 * Get action item at an index
	 * 
	 * @param index
	 *            Index of item (position from callback)
	 * 
	 * @return Action Item at the position
	 */
	public PopupActionItem getActionItem(int index) {
		return actionItems.get(index);
	}

	/**
	 * Set root view.
	 * 
	 * @param id
	 *            Layout resource id
	 */
	public void setRootViewId(int id) {
		mRootView = (ViewGroup) mInflater.inflate(id, null);
		mTrack = (ViewGroup) mRootView.findViewById(R.id.tracks);

		mScroller = (ScrollView) mRootView.findViewById(R.id.scroller);

		// This was previously defined on show() method, moved here to prevent
		// force close that occured
		// when tapping fastly on a view to show quickaction dialog.
		// Thanx to zammbi (github.com/zammbi)
		mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		setContentView(mRootView);
	}

	/**
	 * Set listener for action item clicked.
	 * 
	 * @param listener
	 *            Listener
	 */
	public void setOnActionItemClickListener(OnActionItemClickListener listener) {
		mItemClickListener = listener;
	}

	/**
	 * Add action item
	 * 
	 * @param action
	 *            {@link PopupActionItem}
	 */
	public void addActionItem(PopupActionItem action) {
		actionItems.add(action);

		String title = action.getTitle();
//		Drawable icon = action.getIcon();

		View container;

		container = mInflater.inflate(R.layout.popup_menu_action_item_vertical, null);

//		ImageView img = (ImageView) container.findViewById(R.id.iv_icon);
		TextView text = (TextView) container.findViewById(R.id.tv_title);

//		if (icon != null) {
//			img.setImageDrawable(icon);
//		} else {
//			img.setVisibility(View.GONE);
//		}

		if (title != null) {
			text.setText(title);
		} else {
			text.setVisibility(View.GONE);
		}

		final int pos = mChildPos;
		final int actionId = action.getActionId();

		container.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mItemClickListener != null) {
					mItemClickListener.onItemClick(TopPopupMenuAction.this, pos,
							actionId);
				}

				if (!getActionItem(pos).isSticky()) {
					mDidAction = true;

					dismiss();
				}
			}
		});

		container.setFocusable(true);
		container.setClickable(true);

		mTrack.addView(container, mInsertPos);

		mChildPos++;
		mInsertPos++;
	}
	
	/**
	 * Show quickaction popup. Popup is automatically positioned, on top or
	 * bottom of anchor view.
	 * 
	 */
	public void show(View anchor) {
		preShow();

//		int xPos, yPos;
//
//		mDidAction = false;
//
//		int[] location = new int[2];
//
//		anchor.getLocationOnScreen(location);
//
//		Rect anchorRect = new Rect(location[0], location[1], location[0]
//				+ anchor.getWidth(), location[1] + anchor.getHeight());
//
//		// mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//		// LayoutParams.WRAP_CONTENT));
//
//		mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//
//		int rootHeight = mRootView.getMeasuredHeight();
//
//		if (rootWidth == 0) {
//			rootWidth = mRootView.getMeasuredWidth();
//		}
//
//		int screenWidth = mWindowManager.getDefaultDisplay().getWidth();
//		int screenHeight = mWindowManager.getDefaultDisplay().getHeight();
//
//		// automatically get X coord of popup (top left)
//		if ((anchorRect.left + rootWidth) > screenWidth) {
//			xPos = anchorRect.left - (rootWidth - anchor.getWidth());
//			xPos = (xPos < 0) ? 0 : xPos;
//
//		} else {
//			if (anchor.getWidth() > rootWidth) {
//				xPos = anchorRect.centerX() - (rootWidth / 2);
//			} else {
//				xPos = anchorRect.left;
//			}
//		}
//
//		int dyTop = anchorRect.top;
//		int dyBottom = screenHeight - anchorRect.bottom;
//
//		boolean onTop = (dyTop > dyBottom) ? true : false;
//
//		if (onTop) {
//			if (rootHeight > dyTop) {
//				yPos = 15;
//				LayoutParams l = mScroller.getLayoutParams();
//				l.height = dyTop - anchor.getHeight();
//			} else {
//				yPos = anchorRect.top - rootHeight;
//			}
//		} else {
//			yPos = anchorRect.bottom;
//
//			if (rootHeight > dyBottom) {
//				LayoutParams l = mScroller.getLayoutParams();
//				l.height = dyBottom;
//			}
//		}
		/*
		 * delete the last item's divider
		 */
		View lastView = mTrack.getChildAt(mChildPos - 1);
		lastView.findViewById(R.id.popup_menu_action_item_divider).setVisibility(View.GONE);

		mWindow.setAnimationStyle(R.style.anim_option_menu_popup);

		mWindow.showAsDropDown(anchor);// .showAtLocation(anchor,
												// Gravity.NO_GRAVITY, xPos,
												// yPos);
	}

	/**
	 * Set listener for window dismissed. This listener will only be fired if
	 * the quicakction dialog is dismissed by clicking outside the dialog or
	 * clicking on sticky item.
	 */
	public void setOnDismissListener(TopPopupMenuAction.OnDismissListener listener) {
		setOnDismissListener(this);

		mDismissListener = listener;
	}

	@Override
	public void onDismiss() {
		if (!mDidAction && mDismissListener != null) {
			mDismissListener.onDismiss();
		}
	}

	/**
	 * Listener for item click
	 * 
	 */
	public interface OnActionItemClickListener {
		public abstract void onItemClick(TopPopupMenuAction source, int pos,
				int actionId);
	}

	/**
	 * Listener for window dismiss
	 * 
	 */
	public interface OnDismissListener {
		public abstract void onDismiss();
	}
}