/* ****************************************************************************************************
IusCL - http://iuscl.org

This software is distributed under the terms of:
Eclipse Public License v1.0 - http://www.eclipse.org/org/documents/epl-v10.html
**************************************************************************************************** */
package org.iuscl.samples.allcomponents.iuscl;

import java.util.EnumSet;

import org.iuscl.buttons.IusCLBitButton;
import org.iuscl.buttons.IusCLSpeedButton;
import org.iuscl.comctrls.IusCLImageList;
import org.iuscl.comctrls.IusCLListItem;
import org.iuscl.comctrls.IusCLListView;
import org.iuscl.comctrls.IusCLPageControl;
import org.iuscl.comctrls.IusCLProgressBar;
import org.iuscl.comctrls.IusCLTabSheet;
import org.iuscl.comctrls.IusCLToolBar;
import org.iuscl.comctrls.IusCLTrackBar;
import org.iuscl.comctrls.IusCLTrackBar.IusCLTrackBarOrientation;
import org.iuscl.comctrls.IusCLTreeNode;
import org.iuscl.comctrls.IusCLTreeView;
import org.iuscl.comctrls.IusCLUpDown;
import org.iuscl.comctrls.IusCLWindowsStatusBar;
import org.iuscl.controls.IusCLKeyboardKey;
import org.iuscl.controls.IusCLSizeConstraints;
import org.iuscl.dialogs.IusCLColorDialog;
import org.iuscl.dialogs.IusCLDialogs;
import org.iuscl.dialogs.IusCLDialogs.IusCLMessageBoxIcon;
import org.iuscl.dialogs.IusCLDialogs.IusCLMessageBoxReturn;
import org.iuscl.dialogs.IusCLFolderDialog;
import org.iuscl.dialogs.IusCLFontDialog;
import org.iuscl.dialogs.IusCLOpenDialog;
import org.iuscl.dialogs.IusCLOpenPictureDialog;
import org.iuscl.dialogs.IusCLSaveDialog;
import org.iuscl.dialogs.IusCLSavePictureDialog;
import org.iuscl.extctrls.IusCLImage;
import org.iuscl.extctrls.IusCLPanel;
import org.iuscl.extctrls.IusCLRadioGroup;
import org.iuscl.forms.IusCLCursor;
import org.iuscl.forms.IusCLForm;
import org.iuscl.forms.IusCLScreen;
import org.iuscl.graphics.IusCLColor;
import org.iuscl.graphics.IusCLColor.IusCLStandardColors;
import org.iuscl.graphics.IusCLPicture;
import org.iuscl.graphics.formats.IusCLIcon;
import org.iuscl.graphics.formats.IusCLJpeg;
import org.iuscl.menus.IusCLMenuItem;
import org.iuscl.menus.IusCLPopupMenu;
import org.iuscl.stdctrls.IusCLButton;
import org.iuscl.stdctrls.IusCLCheckBox;
import org.iuscl.stdctrls.IusCLComboBox;
import org.iuscl.stdctrls.IusCLEdit;
import org.iuscl.stdctrls.IusCLLabel;
import org.iuscl.stdctrls.IusCLListBox;
import org.iuscl.stdctrls.IusCLMemo;
import org.iuscl.stdctrls.IusCLPasswordEdit;
import org.iuscl.stdctrls.IusCLRadioButton;
import org.iuscl.stdctrls.IusCLScrollBar;
import org.iuscl.stdctrls.IusCLScrollBar.IusCLScrollBarKind;
import org.iuscl.stdctrls.IusCLScrollBar.IusCLScrollCode;
import org.iuscl.system.IusCLObject;
import org.iuscl.types.IusCLSize;

/* **************************************************************************************************** */
public class FormComctrls extends IusCLForm {

	/* IusCL Components */
	public IusCLPageControl pageControl1;
	public IusCLTabSheet tabSheet1;
	public IusCLTabSheet tabSheet2;
	public IusCLProgressBar progressBar1;
	public IusCLProgressBar progressBar2;
	public IusCLButton button1;
	public IusCLUpDown upDown1;

	public IusCLTrackBar trackBar1;

	public IusCLEdit edit1;

	public IusCLButton button2;

	public IusCLScrollBar scrollBar1;
	public IusCLTreeView treeView1;
	public IusCLButton button3;

	public IusCLButton button4;

	public IusCLButton button5;

	public IusCLListView listView1;

	public IusCLButton button6;

	public IusCLImageList imageList1;

	public IusCLImageList imageList2;



	public IusCLWindowsStatusBar windowsStatusBar1;



	public IusCLPopupMenu popupMenu1;



	public IusCLMenuItem menuItem1;



	public IusCLMenuItem menuItem2;



	public IusCLMenuItem menuItem3;



	public IusCLMenuItem menuItem4;



	public IusCLMenuItem menuItem5;



	public IusCLComboBox comboBox1;



	public IusCLButton button7;



	public IusCLButton button8;



	public IusCLEdit edit2;



	public IusCLListBox listBox1;



	public IusCLTabSheet tabSheet3;



	public IusCLEdit edit3;



	public IusCLMemo memo1;



	public IusCLTabSheet tabSheet4;



	public IusCLButton button9;



	public IusCLCheckBox checkBox1;



	public IusCLRadioButton radioButton1;



	public IusCLBitButton bitButton1;



	public IusCLSpeedButton speedButton1;



	public IusCLRadioGroup radioGroup1;



	public IusCLCheckBox checkBox2;



	public IusCLRadioButton radioButton2;



	public IusCLRadioButton radioButton3;



	public IusCLTabSheet tabSheet5;



	public IusCLButton button10;



	public IusCLButton button11;



	public IusCLButton button12;



	public IusCLButton button13;



	public IusCLButton button14;



	public IusCLButton button15;



	public IusCLButton button16;



	public IusCLOpenDialog openDialog1;



	public IusCLSaveDialog saveDialog1;



	public IusCLOpenPictureDialog openPictureDialog1;



	public IusCLSavePictureDialog savePictureDialog1;



	public IusCLFolderDialog folderDialog1;



	public IusCLFontDialog fontDialog1;



	public IusCLColorDialog colorDialog1;



	public IusCLPanel panelColor;



	public IusCLMemo memoOpen;



	public IusCLButton button17;



	public IusCLButton button18;



	public IusCLOpenDialog openDialogFilter;



	public IusCLImage image1;



	public IusCLImage image2;



	public IusCLTabSheet tabSheet6;



	public IusCLPageControl pageControl2;



	public IusCLTabSheet tabSheet7;






	public IusCLTabSheet tabSheet8;






	public IusCLPanel panel1;






	public IusCLPanel panel2;






	public IusCLPanel panel3;






	public IusCLMemo memo2;






	public IusCLLabel label1;






	public IusCLToolBar toolBar1;






	public IusCLButton button19;






	public IusCLButton button20;






	public IusCLButton button21;






	public IusCLTabSheet tabSheet9;






	public IusCLMemo memo3;






	public IusCLPasswordEdit passwordEdit1;






	public IusCLButton button22;






	/** button1.OnClick event implementation */
	public void button1Click(IusCLObject sender) {

		progressBar2.stepBy(20);
		progressBar2.stepIt();
	}
	/** trackBar1.OnClick event implementation */
	public void trackBar1Click(IusCLObject sender) {

		edit1.setText("" + trackBar1.getPosition());
	}
	/** button2.OnClick event implementation */
	public void button2Click(IusCLObject sender) {

		trackBar1.setOrientation(IusCLTrackBarOrientation.trVertical);
		scrollBar1.setKind(IusCLScrollBarKind.sbVertical);
	}
	/** scrollBar1.OnScroll event implementation */
	public Integer scrollBar1Scroll(IusCLObject sender, IusCLScrollCode scrollCode, Integer scrollPos) {

		edit1.setText("" + scrollPos);
		return scrollPos;
	}
	/** button3.OnClick event implementation */
	public void button3Click(IusCLObject sender) {

		IusCLTreeNode treeNodeRoot = treeView1.getRootTreeNodes().add();
		
		treeNodeRoot.setCaption("Primul");
		
		IusCLTreeNode treeNode = null;
		
		for (int index = 1; index < 100; index++) {

			treeNode = treeNodeRoot.getChildNodes().add();
			treeNode.setCaption("Primul " + index);
		}
		
//		IusCLTreeNode treeNode = treeNodeRoot.getChildNodes().add();
//		treeNode.setCaption("Primul 1");
//		treeNode = treeNodeRoot.getChildNodes().add();
//		treeNode.setCaption("Primul 2");
//		treeNode = treeNodeRoot.getChildNodes().add();
//		treeNode.setCaption("Primul 3");
		
	}
	/** button4.OnClick event implementation */
	public void button4Click(IusCLObject sender) {

		IusCLDialogs.showMessage(treeView1.getSelectedNodes().get(0).getCaption());
		
		IusCLTreeNode treeNode = treeView1.getSelectedNodes().get(0);
		
		IusCLTreeNode parentTreeNode = treeNode.getParentNode();
		
		parentTreeNode.getChildNodes().delete(parentTreeNode.getChildNodes().indexOf(treeNode));
		//treeNode.getChildNodes().delete(0);
		
		
	}
	/** treeView1.OnExpanded event implementation */
	public void treeView1Expanded(IusCLObject sender, IusCLTreeNode treeNode) {

		System.out.println("treeView1Expanded " + treeNode.getCaption());
	}
	/** treeView1.OnCollapsed event implementation */
	public void treeView1Collapsed(IusCLObject sender, IusCLTreeNode treeNode) {

		System.out.println("treeView1Collapsed " + treeNode.getCaption());
	}
	/** button5.OnClick event implementation */
	public void button5Click(IusCLObject sender) {

		IusCLTreeNode treeNode = treeView1.getSelectedNodes().get(0);
		treeView1.expand(treeNode);
	}
	/** button6.OnClick event implementation */
	public void button6Click(IusCLObject sender) {

		IusCLListItem listItem = null;
		
		for (int index = 1; index < 100; index++) {

			listItem = listView1.getListItems().add(); 
			listItem.setCaption("listItem " + index);
			listItem.setImageIndex(index % 2);
			
			if (index % 2 == 0) {
				
				listItem.getSubItems().add("sub1");
				listItem.getSubItems().add("sub2");
			}
		}

	}
	/** button7.OnClick event implementation */
	public void button7Click(IusCLObject sender) {

		comboBox1.getItems().sort();
	}
	/** comboBox1.OnClick event implementation */
	public void comboBox1Click(IusCLObject sender) {

		System.out.println("comboBox1Click " + comboBox1.getItemIndex());
	}
	/** button8.OnClick event implementation */
	public void button8Click(IusCLObject sender) {

		System.out.println("comboBox1Click " + comboBox1.getItemIndex());
	}
	/** comboBox1.OnConstrainedResize event implementation */
	public void comboBox1ConstrainedResize(IusCLObject sender, IusCLSizeConstraints newSizeConstraints) {

		//
	}
	/** edit2.OnChange event implementation */
	public void edit2Change(IusCLObject sender) {

		//
	}
	/** comboBox1.OnChange event implementation */
	public void comboBox1Change(IusCLObject sender) {

		System.out.println("comboBox1Change " + comboBox1.getSelText());
	}
	/** listView1.OnClick event implementation */
	public void listView1Click(IusCLObject sender) {

		if (listView1.getSelCount() > 0) {
		
			//int index = listView1.getse
			
			System.out.println("listView1Click " + 
			listView1.getListItems().get(listView1.getItemIndex()).getCaption());
		}
		
	}
	/** listBox1.OnMouseHover event implementation */
	public void listBox1MouseHover(IusCLObject sender, Integer x, Integer y) {

		System.out.println("listBox1MouseHover");
	}
	/** treeView1.OnMouseMove event implementation */
	public void treeView1MouseMove(IusCLObject sender, 
		EnumSet<IusCLShiftState> shift, Integer x, Integer y) {

		System.out.println("treeView1MouseMove" + x + " - " + y);
	}
	
	/** memo1.OnChange event implementation */
	public void memo1Change(IusCLObject sender) {

		System.out.println("memo1Change " + 
				memo1.getFirstVisibleLine());
	}

	/** memo1.OnMouseHover event implementation */
	public void memo1MouseHover(IusCLObject sender, Integer x, Integer y) {

		System.out.println("memo1MouseHover " + 
				memo1.getSelStart() + " - " + memo1.getSelLength() + " - " + memo1.getSelText());
	}
	/** radioButton2.OnClick event implementation */
	public void radioButton2Click(IusCLObject sender) {

		System.out.println("radioButton2Click " + radioButton2.getChecked());
	}
	/** checkBox1.OnClick event implementation */
	public void checkBox1Click(IusCLObject sender) {

		System.out.println("checkBox1Click " + checkBox1.getChecked());
	}
	/** button10.OnClick event implementation */
	public void button10Click(IusCLObject sender) {

		IusCLColor color = new IusCLColor(IusCLStandardColors.clBlue);
		//colorDialog1.setColor(color);
		
		if (colorDialog1.execute() == true) {
			
			color = colorDialog1.getColor();
		}
		
		panelColor.setColor(color);
	}
	/** button11.OnClick event implementation */
	public void button11Click(IusCLObject sender) {

		
		if (openDialog1.execute() == true) {
			
			memoOpen.getLines().loadFromFile(openDialog1.getFileName());
			memoOpen.setWordWrap(true);
			memoOpen.setSelStart(1000);
			memoOpen.setSelLength(10);
			memoOpen.setFocus();
		}
	}
	/** button17.OnClick event implementation */
	public void button17Click(IusCLObject sender) {

		edit3.showSelection();
		memo1.showSelection();
	}
	/** button18.OnClick event implementation */
	public void button18Click(IusCLObject sender) {

		if (openDialogFilter.execute() == true) {
			
			IusCLDialogs.showMessage(openDialogFilter.getFileName());
		}
	}
	/** button13.OnClick event implementation */
	public void button13Click(IusCLObject sender) {

		if (saveDialog1.execute() == true) {
			
			//IusCLDialogs.showMessage(openDialogFilter.getFileName());
			
			memoOpen.getLines().saveToFile(saveDialog1.getFileName());
		}
		
	}
	/** button15.OnClick event implementation */
	public void button15Click(IusCLObject sender) {

		
		if (folderDialog1.execute() == true) {
			
			IusCLDialogs.showMessage(folderDialog1.getFolderName());
		}
	}
	/** button16.OnClick event implementation */
	public void button16Click(IusCLObject sender) {

		fontDialog1.setFont(memoOpen.getFont());
		
		if (fontDialog1.execute() == true) {
			
			memoOpen.setFont(fontDialog1.getFont());
		}
		
	}
	/** button12.OnClick event implementation */
	public void button12Click(IusCLObject sender) {

		if (openPictureDialog1.execute() == true) {
			
			image1.getPicture().loadFromFile(openPictureDialog1.getFileName());
		}
	}
	/** button14.OnClick event implementation */
	public void button14Click(IusCLObject sender) {

		IusCLJpeg jpeg = new IusCLJpeg();
		jpeg.loadFromGraphic(image1.getPicture().getGraphic());
		
		jpeg.loadFromGraphic(jpeg.resizeGraphic(200, 100));
		jpeg.setCompressionQuality(1);
		
		image2.getPicture().setGraphic(jpeg);
		
		if (savePictureDialog1.execute() == true) {
			
			image2.getPicture().getGraphic().saveToFile(savePictureDialog1.getFileName());
		}
	}
	/** radioGroup1.OnClick event implementation */
	public void radioGroup1Click(IusCLObject sender) {

		System.out.println("radioGroup1Click " + radioGroup1.getItemIndex());		
	}
	/** pageControl2.OnChange event implementation */
	public void pageControl2Change(IusCLObject sender) {

		System.out.println("pageControl2Change " + pageControl2.getActivePageIndex());
	}
	/** toolBar1Buttons0.OnClick event implementation */
	public void toolBar1Buttons0Click(IusCLObject sender) {

		System.out.println("toolBar1Buttons0Click " + toolBar1.getButtons().get(6).getDown());
	}
	/** toolBar1Buttons4.OnClick event implementation */
	public void toolBar1Buttons4Click(IusCLObject sender) {

		System.out.println("toolBar1Buttons4Click " + toolBar1.getButtons().get(4).getDown());
	}
	/** menuItem4.OnClick event implementation */
	public void menuItem4Click(IusCLObject sender) {

		System.out.println("menuItem4Click from popup");
	}
	/** toolBar1Buttons1.OnClick event implementation */
	public void toolBar1Buttons1Click(IusCLObject sender) {

		System.out.println("toolBar1Buttons1Click from popup");
	}
	/** button19.OnClick event implementation */
	public void button19Click(IusCLObject sender) {

		memoOpen.getFont().getStyle().setItalic(true);
	}
	/** button20.OnClick event implementation */
	public void button20Click(IusCLObject sender) {

		IusCLCursor.loadFromResource("cursorName1", IusCLScreen.class, "resources/cursors/crHSplit.gif", 0, 0);
		
		image1.setCursor("cursorName1");
		
		IusCLCursor.loadFromFile("cursorName2", 
				"C:/Iustin/Programming/_IusCL/IusCLRuntime/res/usr/resources/icons/IusCLIcon.ico", 0, 0);
		
		image1.setCursor("cursorName2");
		
		IusCLPicture picture = new IusCLPicture();
		picture.loadFromFile("C:/Iustin/Programming/_IusCL/IusCLRuntime/res/usr/resources/icons/IusCLIcon.ico");
		
		IusCLIcon icon = (IusCLIcon)picture.getGraphic();
		
		IusCLCursor.loadFromGraphic("cursorName3", icon.get(2), 0, 0);
		
		image1.setCursor("cursorName3");
	}
	/** button21.OnClick event implementation */
	public void button21Click(IusCLObject sender) {

		IusCLDialogs.showMessage("msg");
		
		IusCLDialogs.showinfo("infoMsg");
		IusCLDialogs.showinfo("infoMsg", "infoCaption");
		IusCLDialogs.showinfo("infoMsg", "infoCaption", IusCLMessageBoxIcon.mbiWarning);

		IusCLDialogs.showError("errorMsg");
		IusCLDialogs.showError("errorMsg", "errorCaption");
		
		IusCLMessageBoxReturn ret = IusCLDialogs.showQuestion("questionMsg", "questionCaption");
		
		IusCLDialogs.showMessage(ret.name());
		
	}
	/** memo3.OnClick event implementation */
	public void memo3Click(IusCLObject sender) {

		System.out.println("memo3Click");
	}
	/** memo3.OnCanResize event implementation */
	public Boolean memo3CanResize(IusCLObject sender, IusCLSize newSize) {

//		System.out.println("memo3CanResize");
		return true;
	}
	/** memo3.OnChange event implementation */
	public void memo3Change(IusCLObject sender) {

		System.out.println("memo3Change");
	}
	/** memo3.OnConstrainedResize event implementation */
	public void memo3ConstrainedResize(IusCLObject sender, IusCLSizeConstraints newSizeConstraints) {

//		System.out.println("memo3ConstrainedResize");
	}
	/** memo3.OnContextPopup event implementation */
	public Boolean memo3ContextPopup(IusCLObject sender, Integer x, Integer y) {

		System.out.println("memo3ContextPopup");
		return true;
	}
	/** memo3.OnDoubleClick event implementation */
	public void memo3DoubleClick(IusCLObject sender) {

		System.out.println("memo3DoubleClick");
	}
	/** memo3.OnEnter event implementation */
	public void memo3Enter(IusCLObject sender) {

		System.out.println("memo3Enter");
	}
	/** memo3.OnExit event implementation */
	public void memo3Exit(IusCLObject sender) {

		System.out.println("memo3Exit");
	}
	/** memo3.OnKeyDown event implementation */
	public void memo3KeyDown(IusCLObject sender,
		IusCLKeyboardKey key, EnumSet<IusCLShiftState> shift) {

		System.out.println("memo3KeyDown " + key.getStandardKey());
	}
	/** memo3.OnKeyPress event implementation */
	public void memo3KeyPress(IusCLObject sender, Character keyChar) {

		System.out.println("memo3KeyPress " + keyChar);
		IusCLDialogs.showinfo("memo3KeyPress " + keyChar);
	}
	/** memo3.OnKeyUp event implementation */
	public void memo3KeyUp(IusCLObject sender,
		IusCLKeyboardKey key, EnumSet<IusCLShiftState> shift) {

		System.out.println("memo3KeyUp " + key.getKeyPosition());
	}
	/** memo3.OnMouseDown event implementation */
	public void memo3MouseDown(IusCLObject sender, 
		IusCLMouseButton button, EnumSet<IusCLShiftState> shift, Integer x, Integer y) {

		System.out.println("memo3MouseDown");
	}
	/** memo3.OnMouseEnter event implementation */
	public void memo3MouseEnter(IusCLObject sender) {

		System.out.println("memo3MouseEnter");
	}
	/** memo3.OnMouseExit event implementation */
	public void memo3MouseExit(IusCLObject sender) {

		System.out.println("memo3MouseExit");
	}
	/** memo3.OnMouseHover event implementation */
	public void memo3MouseHover(IusCLObject sender, Integer x, Integer y) {

		System.out.println("memo3MouseHover");
	}
	/** memo3.OnMouseMove event implementation */
	public void memo3MouseMove(IusCLObject sender, 
		EnumSet<IusCLShiftState> shift, Integer x, Integer y) {

		//System.out.println("memo3MouseMove");
	}
	/** memo3.OnMouseUp event implementation */
	public void memo3MouseUp(IusCLObject sender, 
		IusCLMouseButton button, EnumSet<IusCLShiftState> shift, Integer x, Integer y) {

		System.out.println("memo3MouseUp");
	}
	/** memo3.OnMouseWheel event implementation */
	public Boolean memo3MouseWheel(IusCLObject sender, 
		EnumSet<IusCLShiftState> shift, Integer wheelDelta, Integer x, Integer y) {

		System.out.println("memo3MouseWheel");
		return true;
	}
	/** memo3.OnMouseWheelDown event implementation */
	public Boolean memo3MouseWheelDown(IusCLObject sender, 
		EnumSet<IusCLShiftState> shift, Integer x, Integer y) {

		System.out.println("memo3MouseWheelDown");
		return true;
	}
	/** memo3.OnMouseWheelUp event implementation */
	public Boolean memo3MouseWheelUp(IusCLObject sender, 
		EnumSet<IusCLShiftState> shift, Integer x, Integer y) {

		System.out.println("memo3MouseWheelUp");
		return true;
	}
	/** memo3.OnResize event implementation */
	public void memo3Resize(IusCLObject sender) {

//		System.out.println("memo3Resize");
	}
	/** button22.OnClick event implementation */
	public void button22Click(IusCLObject sender) {

		passwordEdit1.copyToClipboard();
	}

}