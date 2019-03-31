import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class MyNotepad extends JFrame implements ActionListener{
	JMenu file,edit,format,view,help,build,close;
	JMenuBar mbar;
	JMenuItem neu,open,save,saveas,pageset,print,exit,undo,cut,copy,paste,delete,find,findnxt,replace,selectal,goto1,timedate,font,compile,run,curtab,altab,viewhelp,about;
    JCheckBoxMenuItem wordwrap,status;
    JScrollPane jspcode,jspconsole;
    JSplitPane panev;
    JTabbedPane jtp;
    JTextArea jtaconsole,jtacount,jtacode;
    ImageIcon imgIcon;
    static MyNotepad me;
    boolean findFlag=false;
	int n;
    int m;
    int start,end;
	UndoManager um=new UndoManager();
	JPanel statusbar;
	JLabel l1,l2,lbl;
	JTextField jtfFind,jtfReplace;
	JButton bfndnxt,breplace,breplaceal,cancel;
	JRadioButton rup,rdown;
	JCheckBox cmtchcse;
	ButtonGroup bg;
	JPanel p1,p2,p3;
	String fileName="",fileNameWithPath="";
	Vector<File> vecFile;
	Vector<JTextArea> vecJtacode;
	Vector<Boolean> vecSaveFlag;
	Vector<Boolean> vecNewFlag;
	

	public MyNotepad() {
		// TODO Auto-generated constructor stub
        setLayout(new BorderLayout());
        
		vecFile=new Vector<>();
    	vecSaveFlag=new Vector<>();
    	vecNewFlag=new Vector<>();
		vecJtacode=new Vector<>();
		vecSaveFlag.addElement(true);
    	
    	//menu bar
    	mbar=new JMenuBar();
    	
    	//menu
    	file=new JMenu("File");
    	edit=new JMenu("Edit");
    	format=new JMenu("Format");
    	view=new JMenu("View");
    	/*build=new JMenu("Build");
    	close=new JMenu("Close Tabs");*/
    	help=new JMenu("Help");

    	// menu File
    	neu=new JMenuItem("New");
    	open=new JMenuItem("Open...");
    	save=new JMenuItem("Save");
    	save.setEnabled(false);
    	saveas=new JMenuItem("Save As...");
    	saveas.setEnabled(false);
    	pageset=new JMenuItem("Page Setup...");
    	print=new JMenuItem("Print...");
    	exit=new JMenuItem("Exit");
    	
    	// menu Edit
    	undo=new JMenuItem("Undo");
    	undo.setEnabled(false);
    	cut=new JMenuItem("Cut");
    	cut.setEnabled(false);
    	copy=new JMenuItem("Copy");
    	copy.setEnabled(false);
    	paste=new JMenuItem("Paste");
    	delete=new JMenuItem("Delete");
    	delete.setEnabled(false);
    	find=new JMenuItem("Find...");
    	find.setEnabled(false);
    	findnxt=new JMenuItem("Find Next");
    	findnxt.setEnabled(false);
    	replace=new JMenuItem("Replace...");
    	goto1=new JMenuItem("Go To...");
    	selectal=new JMenuItem("Select All");
    	timedate=new JMenuItem("Time/Date");
    	
    	// menu Format
    	font=new JMenuItem("Font...");
    	wordwrap=new JCheckBoxMenuItem("Word Wrap");

    	// menu view
    	status=new JCheckBoxMenuItem("Status Bar");
    	
    	/*// menu build
    	compile=new JMenuItem("Compile");
    	run=new JMenuItem("Run");
    	run.setEnabled(false);
    	
    	// menu close
    	curtab=new JMenuItem("Current Tab");
    	curtab.setEnabled(false);
    	altab=new JMenuItem("All Tabs");
    	altab.setEnabled(false);*/
    	
    	// menu help
    	viewhelp=new JMenuItem("View Help");
        about=new JMenuItem("About");
        
        file.add(neu);
        file.add(open);
        file.add(save);
        file.add(saveas);
        file.addSeparator();
        file.add(pageset);
        file.add(print);
        file.addSeparator();
        file.add(exit);
        
        edit.add(undo);
        edit.addSeparator();
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);
        edit.addSeparator();
        edit.add(find);
        edit.add(findnxt);
        edit.add(replace);
        edit.add(selectal);
        edit.addSeparator();
        edit.add(goto1);
        edit.add(timedate);
       
        format.add(wordwrap);
        format.add(font);
        
        view.add(status);
        
        /*build.add(compile);
        build.add(run);
        
        close.add(curtab);
        close.add(altab);*/
        
        help.add(viewhelp);
        help.addSeparator();
        help.add(about);
        
        mbar.add(file);
        mbar.add(edit);
        mbar.add(format);
        mbar.add(view);
        //mbar.add(build);
        //mbar.add(close);
        mbar.add(help);
        
        neu.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        saveas.addActionListener(this);
        pageset.addActionListener(this);
        print.addActionListener(this);
        exit.addActionListener(this);
        
        undo.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        delete.addActionListener(this);
        find.addActionListener(this);
        findnxt.addActionListener(this);
        replace.addActionListener(this);
        selectal.addActionListener(this);
        goto1.addActionListener(this);
        timedate.addActionListener(this);
        
        wordwrap.addActionListener(this);
        font.addActionListener(this);
        
        status.addActionListener(this);
        
        /*compile.addActionListener(this);
        run.addActionListener(this);
        
        curtab.addActionListener(this);
        altab.addActionListener(this);*/
                
        viewhelp.addActionListener(this);
        about.addActionListener(this);
        
        jtaconsole=new JTextArea();
		jtaconsole.setEditable(false);
		jtaconsole.setLineWrap(true);
		jtaconsole.setWrapStyleWord(true);
        jspconsole=new JScrollPane(jtaconsole);
                
        jtp=new JTabbedPane();
        jtp=new JTabbedPane(JTabbedPane.BOTTOM);
		jtp.setPreferredSize(new Dimension(500,270));
		jtp.setMinimumSize(new Dimension(500,270));
        
        panev=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		panev.setTopComponent(jtp);
		panev.setBottomComponent(jspconsole);
		panev.setPreferredSize(new Dimension(500,450));
		add(panev);
        
        setJMenuBar(mbar);
        imgIcon =new ImageIcon("images/notepad.jpg");
        setIconImage (imgIcon.getImage());
        setTitle("MyNotepad");
        setSize(500,500);
        setVisible(true);
        
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyNotepad();
	} 
	
	void txtUpdate()
	{
		vecJtacode.get(jtp.getSelectedIndex()).getDocument().addDocumentListener(new DocumentListener() {     	
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				vecSaveFlag.setElementAt(false, jtp.getSelectedIndex());
				if(vecJtacode.get(jtp.getSelectedIndex()).getText()!=null)
				{
					if(vecJtacode.get(jtp.getSelectedIndex()).isRequestFocusEnabled()==true)
			        cut.setEnabled(true);
				    copy.setEnabled(true);
					delete.setEnabled(true);
					find.setEnabled(true);
					findnxt.setEnabled(true);
				}

			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				if(vecJtacode.get(jtp.getSelectedIndex()).getText()!=null)
					vecSaveFlag.setElementAt(false, jtp.getSelectedIndex());
				if(vecJtacode.get(jtp.getSelectedIndex()).getText()!=null)
				{
				   cut.setEnabled(true);
				   copy.setEnabled(true);
				   delete.setEnabled(true);
				   find.setEnabled(true);
				   findnxt.setEnabled(true);
				}

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				vecSaveFlag.setElementAt(false, jtp.getSelectedIndex());
				if(vecJtacode.get(jtp.getSelectedIndex()).getText()!=null)
				{
			    	cut.setEnabled(true);
			        copy.setEnabled(true);
					delete.setEnabled(true);
					find.setEnabled(true);
					findnxt.setEnabled(true);
				}

			}
        });
        
        vecJtacode.get(jtp.getSelectedIndex()).getDocument().addUndoableEditListener(new UndoableEditListener() 
        {
        	public void undoableEditHappened(UndoableEditEvent ue)
        	{
        		um.addEdit(ue.getEdit());
        		if(um.canUndo())
        			undo.setEnabled(true);
        	}
        });
        
        vecJtacode.get(jtp.getSelectedIndex()).addCaretListener(new CaretListener()
        {
        	public void caretUpdate(CaretEvent ce)
        	{
        		start=ce.getDot();
        		end=ce.getMark();
        	}
        });
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String s=arg0.getActionCommand();
		if(s.equals("New")) {
	    	vecNewFlag.addElement(true);
			opennew();
			if(jtp.isFocusable()==true && vecNewFlag.get(jtp.getSelectedIndex())==false)
			{
				save.setEnabled(true);
				saveas.setEnabled(true);
				curtab.setEnabled(true);
				altab.setEnabled(true);
			}
		}
		
		else if(s.equals("Open...")){
			open();
			if(jtp.isFocusable()==true && vecNewFlag.get(jtp.getSelectedIndex())==false)
			{
				save.setEnabled(true);
				saveas.setEnabled(true);
                curtab.setEnabled(true);
                altab.setEnabled(true);
			}
		}
		
		else if(s.equals("Save")){
		    save();
		}
		else if(s.equals("Save As...")) {
			saveas();
   		}
		else if(s.equals("Exit")) {
			exit();
		}
		else if(s.equals("Page Setup...")) {
			
		}
		else if(s.equals("Print...")) {
			
		}
		else if(s.equals("Undo")) {
			undo();
		}
		else if(s.equals("Cut")) {
			cut();
		}
		else if(s.equals("Copy")) {
			copy();
		}
		else if(s.equals("Paste")) {
			paste();
		}
		else if(s.equals("Delete")) {
			delete();
		}
		else if(s.equals("Find...")) {
			find();
		}
		else if(s.equals("Find Next")) {
			if(findFlag==true)
				findnxt();
			else
				find();
		}
		else if(s.equals("Replace...")) {
			replace();
		}
		else if(s.equals("Go To...")) {
			
		}
		else if(s.equals("Select All")) {
			vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(0);
			vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(vecJtacode.get(jtp.getSelectedIndex()).getText().length());
		}
		else if(s.equals("Time/Date")) {
			java.util.Date d1=new java.util.Date();
			SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
			vecJtacode.get(jtp.getSelectedIndex()).insert(sdf.format(d1),vecJtacode.get(jtp.getSelectedIndex()).getCaretPosition());
		}
		else if(s.equals("Font")) {
			
		}
		else if(s.equals("Word Wrap")) {
			vecJtacode.get(jtp.getSelectedIndex()).setLineWrap(wordwrap.getState());
		}
		else if(s.equals("Status Bar")) {
			statusbar=new JPanel();
			statusbar.setBackground(Color.LIGHT_GRAY);
			lbl=new JLabel("");
			lbl.setPreferredSize(new Dimension(500,30));
			statusbar.add(lbl);	
			add(statusbar,BorderLayout.SOUTH);
		}
		/*else if(s.equals("Compile")) {
			jtaconsole.setText("");
			compile();
			if(jtaconsole.getText().equals("Compilation Successful")) 
				run.setEnabled(true);
		}
		else if(s.equals("Run")) {
			run();
		}
		else if(s.equals("Current Tab")) {
			curtab();
		}
		else if(s.equals("All Tabs")) {
			alltab();
		}*/
		else if(s.equals("View Help")) {
			
		}
		else if(s.equals("About")) {
			
		}
			
	}
	
	//fun to open new window
	void opennew()
	{
      	jtacount=new JTextArea("1");
       	jtacount.setBackground(Color.GRAY);
       	jtacount.setEditable(false);
       	jtacount.setSize(new Dimension(50,1000));
        	
       	jtacode=new JTextArea();
       	jtacode.setText("");
        jspcode=new JScrollPane(jtacode);
        jspcode.setBackground(Color.WHITE);
        jspcode.setRowHeaderView(jtacount);
        jtp.addTab("",jspcode);
        jtp.setSelectedIndex(jtp.getTabCount()-1);
        saveas();
        jtp.setTitleAt(jtp.getSelectedIndex(), vecFile.get(jtp.getSelectedIndex()).getName());
        jtacode.getDocument().addDocumentListener(new DocumentListener() {
			String getText(){
				int lines=jtacode.getLineCount();
				String s1="1";
				for(int i=2;i<=lines;i++) {
					s1+="\n"+i;
				}
				return s1;
			}
			public void changedUpdate(DocumentEvent de) {
				jtacount.setText(getText());
			}
			public void insertUpdate(DocumentEvent de) {
        	   jtacount.setText(getText());
			}
			public void removeUpdate(DocumentEvent de) {
        	   jtacount.setText(getText());
			}
        });
        vecJtacode.addElement(jtacode);
        txtUpdate();

  	}
	
	//fun to open
	void open()
	{
        JFileChooser jfc=new JFileChooser("This PC");
	    FileNameExtensionFilter filter1=new FileNameExtensionFilter("Text Files","txt");
	    FileNameExtensionFilter filter2=new FileNameExtensionFilter("Image Files","png","jpg","img","gif");
	    FileNameExtensionFilter filter3=new FileNameExtensionFilter("Java Files","java");
	    jfc.addChoosableFileFilter(filter1);
	    jfc.addChoosableFileFilter(filter2);
	    jfc.addChoosableFileFilter(filter3);
	    jfc.setFileFilter(filter3);
	    int code=jfc.showOpenDialog(me);
	    if(code == JFileChooser.APPROVE_OPTION) {
	   		try {
	   			fileNameWithPath=jfc.getSelectedFile().getAbsolutePath();
	   			fileName=jfc.getSelectedFile().getName();
	    		FileInputStream fis=new FileInputStream(fileNameWithPath);
		        int n=0;
		        String s1 ="";
		        while((n=fis.read())!=-1) {
	         	    s1+=((char)n+"");
			        } 
		        
		        jtacode=new JTextArea();
		        jspcode=new JScrollPane(jtacode);
	            jspcode.setBackground(Color.WHITE);
	            
	        	jtacount=new JTextArea("1");
	           	jtacount.setBackground(Color.LIGHT_GRAY);
	           	jtacount.setEditable(false);
	           	jtacount.setSize(new Dimension(70,1000));
	            	
	            jspcode.setRowHeaderView(jtacount);
	            jtacode.getDocument().addDocumentListener(new DocumentListener() {
	    			String getText(){
	    				int lines=jtacode.getLineCount();
	    				String s1="1";
	    				for(int i=2;i<=lines;i++) {
	    					s1+="\n"+i;
	    				}
	    				return s1;
	    			}
	    			public void changedUpdate(DocumentEvent de) {
	    				jtacount.setText(getText());
	    			}
	    			public void insertUpdate(DocumentEvent de) {
	            	   jtacount.setText(getText());
	    			}
	    			public void removeUpdate(DocumentEvent de) {
	            	   jtacount.setText(getText());
	    			}
	            });
	            jtacode.setText(s1);
		        jtp.addTab(fileName, jspcode);
		        jtp.setSelectedIndex(jtp.getTabCount()-1);
		        fis.close();
		        vecFile.addElement(new File(fileNameWithPath));
	            vecJtacode.addElement(jtacode);
		        txtUpdate();		        
		        vecNewFlag.addElement(false);
		        vecSaveFlag.addElement(true);
			    }
		   catch (FileNotFoundException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		       }
	       catch (IOException e) {
		        // TODO Auto-generated catch block
			    e.printStackTrace();
		        }
		   }
		   else
			    return;		    		   
	}
	
	//fun to save for first time
	void saveas()
	{
		JFileChooser jfc=new JFileChooser("This PC");
		FileNameExtensionFilter filter1=new FileNameExtensionFilter("Text Files","txt");
		FileNameExtensionFilter filter2=new FileNameExtensionFilter("Image Files","png","jpg","img","gif");
	    FileNameExtensionFilter filter3=new FileNameExtensionFilter("Java Files","java");
		jfc.addChoosableFileFilter(filter1);
		jfc.addChoosableFileFilter(filter2);
	    jfc.addChoosableFileFilter(filter3);
		jfc.setFileFilter(filter3);
		int code=jfc.showSaveDialog(me);
        if(code==JFileChooser.APPROVE_OPTION) {
        	try {
        		if(vecNewFlag.get(jtp.getSelectedIndex())==true)
        		{
            		fileNameWithPath=jfc.getSelectedFile().getAbsolutePath();
            		if(!fileNameWithPath.endsWith(".java"))
            			fileNameWithPath+=".java";
    				FileWriter fw=new FileWriter(fileNameWithPath);
            		vecFile.addElement(new File(fileNameWithPath));
    				fileName=jfc.getSelectedFile().getName();
    				if(!fileName.endsWith(".java"))
            			fileName+=".java";
    				fw.close();
        		}
        		else
        		{
            		fileNameWithPath=jfc.getSelectedFile().getAbsolutePath();
            		if(!fileNameWithPath.endsWith(".java"))
            			fileNameWithPath+=".java";
       				fileName=jfc.getSelectedFile().getName();
    				if(!fileName.endsWith(".java"))
            			fileName+=".java";
    				jtp.setTitleAt(jtp.getSelectedIndex(), fileName);
    				FileWriter fw=new FileWriter(fileNameWithPath);
    				fw.write(vecJtacode.get(jtp.getSelectedIndex()).getText());
    				vecFile.get(jtp.getSelectedIndex()).delete();
        			vecFile.removeElementAt(jtp.getSelectedIndex());
    				vecFile.addElement(new File(fileNameWithPath));
    				fw.close();
        		}
			}
        	catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else
        {
        	dispose();
        }
        vecNewFlag.setElementAt(false,jtp.getSelectedIndex());
        vecSaveFlag.setElementAt(true, jtp.getSelectedIndex());
	}
	
	//fun to save again
	void save()
	{
		try {
			FileWriter fw=new FileWriter(vecFile.get(jtp.getSelectedIndex()));
			fw.write(vecJtacode.get(jtp.getSelectedIndex()).getText());
			fw.close();
			}
    	catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vecSaveFlag.setElementAt(true, jtp.getSelectedIndex());
	}
	
    //fun to exit
	void exit()
	{
		if(vecNewFlag.isEmpty()==false && vecSaveFlag.get(jtp.getSelectedIndex())==false) {
			int ans=JOptionPane.showOptionDialog(this,"Do you want to save changes to "+fileName+"?","My Notepad",0,JOptionPane.QUESTION_MESSAGE,null,new String[] {"Save","Don't Save","Cancel"},"Save");
			if (ans==0) {
				save();
			}
			else if(ans==1) {
                dispose();
        		vecSaveFlag.setElementAt(true, jtp.getSelectedIndex());
                return;
			}
			else{
				return;
			}		
		}
		else {
			dispose();
			vecSaveFlag.setElementAt(true, jtp.getSelectedIndex());
		}
	}
	
	//fun to undo any edit
	void undo()
	{
			um.undo();
	}
	
	//fun to cut a block
	void cut()
	{
		StringSelection sel=new StringSelection(vecJtacode.get(jtp.getSelectedIndex()).getSelectedText());
		Clipboard clpb=Toolkit.getDefaultToolkit().getSystemClipboard();
		clpb.setContents(sel,sel);
		n=vecJtacode.get(jtp.getSelectedIndex()).getSelectionStart();
		m=vecJtacode.get(jtp.getSelectedIndex()).getSelectionEnd();
		StringBuffer sb = new StringBuffer(vecJtacode.get(jtp.getSelectedIndex()).getText());
		sb.delete(n,m);
		vecJtacode.get(jtp.getSelectedIndex()).setText(sb.toString());
	}
	
	//fun to copy a block
	void copy()
	{
		StringSelection sel=new StringSelection(vecJtacode.get(jtp.getSelectedIndex()).getSelectedText());
		Clipboard clpb=Toolkit.getDefaultToolkit().getSystemClipboard();
		clpb.setContents(sel,sel);
	}
	
	//fun to paste block
	void paste()
	{
		Clipboard clpb=Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable tf=clpb.getContents(this);
		n=vecJtacode.get(jtp.getSelectedIndex()).getCaretPosition();
		try {
			String str=(String)tf.getTransferData(DataFlavor.stringFlavor);
			vecJtacode.get(jtp.getSelectedIndex()).insert(str,vecJtacode.get(jtp.getSelectedIndex()).getCaretPosition());
		} 
		catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	//fun to delete a particular block 
	void delete()
	{
		n=vecJtacode.get(jtp.getSelectedIndex()).getSelectionStart();
		m=vecJtacode.get(jtp.getSelectedIndex()).getSelectionEnd();
		StringBuffer sb = new StringBuffer(vecJtacode.get(jtp.getSelectedIndex()).getText());
		sb.delete(n,m);
		vecJtacode.get(jtp.getSelectedIndex()).setText(sb.toString());
	}
	
	//fun to find a word from the whole text
	void find()
	{
		JDialog finddlog=new JDialog(this,"Find",Dialog.ModalityType.MODELESS);
		l1=new JLabel("Find What");
		jtfFind=new JTextField(15);
		bfndnxt=new JButton("Find Next");
		cancel=new JButton("Cancel");
		rup=new JRadioButton("UP");
		rdown=new JRadioButton("DOWN",true);
		cmtchcse=new JCheckBox("Match Case");
		bg=new ButtonGroup();
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		
		p1.setLayout(new GridLayout(2,1));
		
		bg.add(rup);
		bg.add(rdown);
		
		p1.add(bfndnxt);
		p1.add(cancel);
		
		p2.add(rup);
		p2.add(rdown);
		
		p3.add(l1);
		p3.add(jtfFind);
		
		finddlog.add(p3,"North");
		finddlog.add(p1,"East");
		finddlog.add(p2);
		finddlog.add(cmtchcse,"South");
						
		finddlog.setSize(400,200);
		finddlog.setVisible(true);
		finddlog.setLocation(100,150);
		
		bfndnxt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		if(vecJtacode.get(jtp.getSelectedIndex()).getText()!=null)
	    		{
	    			if(jtfFind.getText()!=null)
	    			{
                        if(cmtchcse.isSelected()==true)
                        {
                            if(rdown.isSelected()==true)
                            {
        	    	    		int startpos=start;
        	    			    int findpos=vecJtacode.get(jtp.getSelectedIndex()).getText().toUpperCase().indexOf(jtfFind.getText().toUpperCase(),startpos);
        	    			    if(findpos!=-1)
        	    			    {
        	    		           vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(findpos);
        	    	     	       vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(findpos+jtfFind.getText().length());
        	    	     	       findFlag=true;
        	    			    }
        	    			    else
        	    			    {
        	    			    	JOptionPane.showMessageDialog(MyNotepad.this,"cannot find "+ jtfFind.getText(), "MyNotepad",JOptionPane.INFORMATION_MESSAGE);
        	    			    }
                            }
                            else if(rup.isSelected()==true)
                            {
        	    	    		int startpos=end;
        	    			    int findpos=vecJtacode.get(jtp.getSelectedIndex()).getText().toUpperCase().lastIndexOf(jtfFind.getText().toUpperCase(),startpos);
        	    			    if(findpos!=-1)
        	    			    {
        	    		           vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(findpos);
        	    	     	       vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(findpos+jtfFind.getText().length());
           	    			       findFlag=true;
        	    			    }
        	    			    else
        	    			    {
        	    			    	JOptionPane.showMessageDialog(MyNotepad.this,"cannot find "+ jtfFind.getText(), "MyNotepad",JOptionPane.INFORMATION_MESSAGE);
        	    			    }
        	    			    if(findFlag==true)
        	    			    {
            	    			    end-=jtfFind.getText().length()+1;
        	    			    }
                            }
                        }
                        else
                        {
                        	if(rdown.isSelected()==true)
                            {
        	    	    		int startpos=start;
        	    			    int findpos=vecJtacode.get(jtp.getSelectedIndex()).getText().indexOf(jtfFind.getText(),startpos);
        	    			    if(findpos!=-1)
        	    			    {
        	    		           vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(findpos);
        	    	     	       vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(findpos+jtfFind.getText().length());
        	    	     	       findFlag=true;
        	    			    }
        	    			    else
        	    			    {
        	    			    	JOptionPane.showMessageDialog(MyNotepad.this,"cannot find "+ jtfFind.getText(), "MyNotepad",JOptionPane.INFORMATION_MESSAGE);
        	    			    }
                            }
                            else if(rup.isSelected()==true)
                            {
        	    	    		int startpos=end;
        	    			    int findpos=vecJtacode.get(jtp.getSelectedIndex()).getText().lastIndexOf(jtfFind.getText(),startpos);
        	    			    if(findpos!=-1)
        	    			    {
        	    		           vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(findpos);
        	    	     	       vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(findpos+jtfFind.getText().length());
        	    	     	       findFlag=true;
        	    			    }
        	    			    else
        	    			    {
        	    			    	JOptionPane.showMessageDialog(MyNotepad.this,"cannot find "+ jtfFind.getText(), "MyNotepad",JOptionPane.INFORMATION_MESSAGE);
        	    			    }
        	    			    if(findFlag==true)
        	    			    {
            	    			    end-=jtfFind.getText().length()+1;
        	    			    }
                            }
                        }
	    			}
	    		}
	    	}
		});
		
		cancel.addActionListener(new ActionListener()
	    {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				finddlog.dispose();
			}
			
		});
	}
	
	//fun to find a string if found once already
	void findnxt()
	{
		if(vecJtacode.get(jtp.getSelectedIndex()).getText()!=null)
		{
			if(jtfFind.getText()!=null)
			{
                if(cmtchcse.isSelected()==true)
                {
                    if(rdown.isSelected()==true)
                    {
	    	    		int startpos=start;
	    			    int findpos=vecJtacode.get(jtp.getSelectedIndex()).getText().toUpperCase().indexOf(jtfFind.getText().toUpperCase(),startpos);
	    			    if(findpos!=-1)
	    			    {
	    		           vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(findpos);
	    	     	       vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(findpos+jtfFind.getText().length());
	    	     	       findFlag=true;
   	    			       breplace.setEnabled(true);
	    			    }
	    			    else
	    			    {
	    			    	JOptionPane.showMessageDialog(MyNotepad.this,"cannot find "+ jtfFind.getText(), "MyNotepad",JOptionPane.INFORMATION_MESSAGE);
	    			    }
                    }
                    else if(rup.isSelected()==true)
                    {
	    	    		int startpos=end;
	    			    int findpos=vecJtacode.get(jtp.getSelectedIndex()).getText().toUpperCase().lastIndexOf(jtfFind.getText().toUpperCase(),startpos);
	    			    if(findpos!=-1)
	    			    {
	    		           vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(findpos);
	    	     	       vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(findpos+jtfFind.getText().length());
   	    			       findFlag=true; 
   	    			       breplace.setEnabled(true);
               		    }
	    			    else
	    			    {
	    			    	JOptionPane.showMessageDialog(MyNotepad.this,"cannot find "+ jtfFind.getText(), "MyNotepad",JOptionPane.INFORMATION_MESSAGE);
	    			    }
	    			    if(findFlag==true)
	    			    {
    	    			    end-=jtfFind.getText().length()+1;
    	    			    breplace.setEnabled(true);
	    			    }
                    }
                }
                else
                {
                	if(rdown.isSelected()==true)
                    {
	    	    		int startpos=start;
	    			    int findpos=vecJtacode.get(jtp.getSelectedIndex()).getText().indexOf(jtfFind.getText(),startpos);
	    			    if(findpos!=-1)
	    			    {
	    		           vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(findpos);
	    	     	       vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(findpos+jtfFind.getText().length());
	    	     	       findFlag=true;
   	    			       breplace.setEnabled(true);
	    			    }
	    			    else
	    			    {
	    			    	JOptionPane.showMessageDialog(MyNotepad.this,"cannot find "+ jtfFind.getText(), "MyNotepad",JOptionPane.INFORMATION_MESSAGE);
	    			    }
                    }
                    else if(rup.isSelected()==true)
                    {
	    	    		int startpos=end;
	    			    int findpos=vecJtacode.get(jtp.getSelectedIndex()).getText().lastIndexOf(jtfFind.getText(),startpos);
	    			    if(findpos!=-1)
	    			    {
	    		           vecJtacode.get(jtp.getSelectedIndex()).setSelectionStart(findpos);
	    	     	       vecJtacode.get(jtp.getSelectedIndex()).setSelectionEnd(findpos+jtfFind.getText().length());
	    	     	       findFlag=true;
   	    			       breplace.setEnabled(true);
	    			    }
	    			    else
	    			    {
	    			    	JOptionPane.showMessageDialog(MyNotepad.this,"cannot find "+ jtfFind.getText(), "MyNotepad",JOptionPane.INFORMATION_MESSAGE);
	    			    }
	    			    if(findFlag==true)
	    			    {
    	    			    end-=jtfFind.getText().length()+1;
    	    			    breplace.setEnabled(true);
	    			    }
                    }
                }
			}
		}
	}
	
	//fun to replace a string with another string in the text
	void replace()
	{
		JDialog replacedlog=new JDialog(this,"Replace",Dialog.ModalityType.MODELESS);
		l1=new JLabel("Find What");
		jtfFind=new JTextField(15);
		l2=new JLabel("Replace With");
		jtfReplace=new JTextField(15);
		jtfReplace=new JTextField(15);
		bfndnxt=new JButton("Find Next");
		breplace=new JButton("Replace");
		breplace.setEnabled(false);
		breplaceal=new JButton("Replace All");
		cancel=new JButton("Cancel");
		rup=new JRadioButton("UP");
		rdown=new JRadioButton("DOWN",true);
		cmtchcse=new JCheckBox("Match Case");
		bg=new ButtonGroup();
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		
		p1.setLayout(new GridLayout(4,1));
		p3.setLayout(new GridLayout(2,2));
		
		bg.add(rup);
		bg.add(rdown);
		
		p1.add(bfndnxt);
		p1.add(breplace);
		p1.add(breplaceal);
		p1.add(cancel);
		
		p2.add(rup);
		p2.add(rdown);
		
		p3.add(l1);
		p3.add(jtfFind);
		p3.add(l2);
		p3.add(jtfReplace);
		
		replacedlog.add(p3,"North");
		replacedlog.add(p1,"East");
		replacedlog.add(p2);
		replacedlog.add(cmtchcse,"South");
						
		replacedlog.setSize(400,200);
		replacedlog.setVisible(true);
		replacedlog.setLocation(100,150);
		
		bfndnxt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		findnxt();
	    	}
		});
		
		breplace.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				vecJtacode.get(jtp.getSelectedIndex()).replaceSelection(jtfReplace.getText());
				findnxt();
			}
			
		});
		
		breplaceal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String s=vecJtacode.get(jtp.getSelectedIndex()).getText().replaceAll(jtfFind.getText(), jtfReplace.getText());
				vecJtacode.get(jtp.getSelectedIndex()).setText(s);
			}
			
		});
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				replacedlog.dispose();
			}
			
		});
	}
	
	/*//fun to compile a program on currently selected tab
	void compile()
	{
		save();
		Process pr=null;
		try {
			File f=(vecFile.get(jtp.getSelectedIndex()));
			pr = Runtime.getRuntime().exec("cmd /c javac "+f.getAbsolutePath());
			InputStream is=pr.getErrorStream();
			if(is.read()==-1)
				jtaconsole.setText("Compilation Successful");
			else
			{
				int ch;
				while((ch=is.read())!=-1) {
					jtaconsole.append((char)ch+"");
				}
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//fun to run a program on currently selected tab
	void run()
	{
		Process pr=null;
		try {
			File f=(vecFile.get(jtp.getSelectedIndex()));
			pr=Runtime.getRuntime().exec("cmd /c start cmd /k java "+f.getName().substring(0,f.getName().indexOf(".")),null,new File(f.getParent()));
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	///fun to remove currently opened tab
	void curtab()
	{
		if(vecSaveFlag.get(jtp.getSelectedIndex())==false)
		{
			int ans=JOptionPane.showOptionDialog(this,"Do you want to save changes to "+vecFile.get(jtp.getSelectedIndex())+"?","Close Current Tab",0,JOptionPane.QUESTION_MESSAGE,null,new String[] {"Save","Don't Save","Cancel"},"Save");
            if(ans==0) {
            	save();
            	jtp.remove(jtp.getSelectedIndex());
            }
            else if(ans==1) {
            	jtp.remove(jtp.getSelectedIndex());
            }
            else
            	return;
		}
		else
			jtp.remove(jtp.getSelectedIndex());
	}
	
	//fun to remove all tabs
	void alltab()
	{
		if(vecSaveFlag.isEmpty()==false && vecSaveFlag.get(jtp.getSelectedIndex())==false)
		{
			int ans=JOptionPane.showOptionDialog(this,"Do you want to save changes to "+fileName+"?","Close All Tabs",0,JOptionPane.QUESTION_MESSAGE,null,new String[] {"Save","Don't Save","Cancel"},"Save");
            if(ans==0) {
            	save();
            	jtp.removeAll();
            }
            else if(ans==1) {
            	jtp.removeAll();
            }
            else
            	return;
		}
		else
			jtp.removeAll();
	}*/
}