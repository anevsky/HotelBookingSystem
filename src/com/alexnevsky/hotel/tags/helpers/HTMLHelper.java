package com.alexnevsky.hotel.tags.helpers;

/**
 * Contains html strings and their in-program names.
 * 
 * @version 1.0 08.06.2011
 * @author Alex Nevsky
 */
public class HTMLHelper {

	public static final String P_START_HTML_TAG = "<p>";
	public static final String P_END_HTML_TAG = "</p>";
	public static final String BR_HTML_TAG = "<br />";
	public static final String DOUBLE_BR_HTML_TAG = "<br /><br />";
	public static final String SPACE_HTML_TAG = "&#32;";
	public static final String COLON_HTML_TAG = "&#58;";
	public static final String DOLLAR_HTML_TAG = "&#36;";
	public static final String DATE_FORMAT_YYYYMMDD_TEMPLATE = "yyyy-MM-dd";

	public static final String TABLE_LIGHT_BEGIN_HTML_TAG = "<table id=\"light-table\" cellspacing=\"0\">";
	public static final String TABLE_END_HTML_TAG = "</table>";
	public static final String CAPTION_BEGIN_HTML_TAG = "<caption>";
	public static final String CAPTION_END_HTML_TAG = "</caption>";
	public static final String TR_BEGIN_HTML_TAG = "<tr>";
	public static final String TR_END_HTML_TAG = "</tr>";
	public static final String TD_CLASS_BEGIN = "<td class=\"";
	public static final String TD_END_HTML_TAG = "</td>";
	public static final String TH_COL_NO_BG_HTML_TAG = "<th scope=\"col\" class=\"nobg\">";
	public static final String TH_COL_HTML_TAG = "<th scope=\"col\">";
	public static final String TH_END_HTML_TAG = "</th>";
	public static final String TH_SPEC_CLASS = "spec";
	public static final String TH_SPEC_ALT_CLASS = "specalt";
	public static final String TD_EMPTY_CLASS = "";
	public static final String TD_ALT_CLASS = "alt";
	public static final String TH_ROW_CLASS_BEGIN = "<th scope=\"row\" class=\"";
	public static final String TAG_END = "\">";
	public static final String UL_CLASS_LINK_BEGIN_HTML_TAG = "<ul class=\"link\">";
	public static final String UL_END_HTML_TAG = "</ul>";
	public static final String HR_HTML_TAG = "<hr />";

	public static final String TD_BEGIN_HTML_TAG = "<td>";
	public static final String LI_BEGIN_HTML_TAG = "<li>";
	public static final String LI_END_HTML_TAG = "</li>";
	public static final String STRONG_COLOR_DARKORCHILD_BEGIN_HTML_TAG = "<strong style=\"color:darkorchid;\">";
	public static final String STRONG_BEGIN_HTML_TAG = "<strong>";
	public static final String STRONG_END_HTML_TAG = "</strong>";
	public static final String MINUS = "-";
	public static final String NUMBER_SIGN_HTML_TAG = "&#35;";
	public static final String COMMA_HTML_TAG = "&#44;";
	public static final String PERIOD_HTML_TAG = "&#46;";
	public static final String VERTICAL_BAR_HTML_TAG = "&#124;";
	public static final String A_HREF_FIND_ROOM = "<a href=\"controller?command=findroom&orderid=";
	public static final String A_HREF_CANCEL_ORDER = "<a href=\"controller?command=cancelorder&orderid=";
	public static final String A_HREF_DELETE_ORDER = "<a href=\"controller?command=deleteorder&orderid=";
	public static final String A_HREF_VIEW_BILL = "<a href=\"controller?command=viewbill&orderid=";
	public static final String A_HREF_CANCEL_MY_ORDER = "<a href=\"controller?command=cancelmyorder&orderid=";
	public static final String A_HREF_DELETE_MY_ORDER = "<a href=\"controller?command=deletemyorder&orderid=";
	public static final String A_HREF_SHOW_MY_BILL = "<a href=\"controller?command=showmybill&orderid=";
	public static final String A_HREF_END = "</a>";

	public static final String FORM_BOOKING_BEGIN_HTML_TAG = "<form name=\"bookingForm\" action=\"controller\" method=\"post\">";
	public static final String FORM_END_HTML_TAG = "</form>";
	public static final String INPUT_SELECT_ROOM_HTML_TAG = "<input type=\"hidden\" name=\"command\" value=\"selectroom\" />";
	public static final String FIELDSET_BEGIN_HTML_TAG = "<fieldset>";
	public static final String FIELDSET_END_HTML_TAG = "</fieldset>";
	public static final String LEGEND_BEGIN_HTML_TAG = "<legend>";
	public static final String LEGEND_END_HTML_TAG = "</legend>";
	public static final String LABEL_ROOM_ID_BEGIN_HTML_TAG = "<label for=\"roomid\">";
	public static final String LABEL_END_HTML_TAG = "</label>";
	public static final String SELECT_ROOM_ID_BEGIN_HTML_TAG = "<select name=\"roomid\" size=\"1\">";
	public static final String SELECT_END_HTML_TAG = "</select>";
	public static final String OPTION_BEGIN_HTML_TAG = "<option>";
	public static final String OPTION_END_HTML_TAG = "</option>";
	public static final String P_SUBMIT_BEGIN_HTML_TAG = "<p class=\"submit\">";
	public static final String INPUT_SUBMIT_SELECT_BEGIN_HTML_TAG = "<input type=\"submit\" name=\"submit\" value=\"";
	public static final String INPUT_END_HTML_TAG = "</input>";

	/**
	 * Helper class, so private constructor.
	 */
	private HTMLHelper() {
	}
}
