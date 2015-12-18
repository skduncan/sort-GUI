package lab9;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;


@SuppressWarnings("serial")
public class SortFrame extends JFrame
{
	private int myList[];
	public static final int MAX_SIZE = 500;
	private int count;
	
	Random generator = new Random();
	
	public static final int FRAMEHEIGHT=350;
	public static final int FRAMEWIDTH=650;
	
	// Buttons and Widgets for the frame
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	JButton reset = new JButton("Reset");
	JRadioButton slowSpeed = new JRadioButton("Slow");
	JRadioButton midSpeed = new JRadioButton("Medium");
	JRadioButton fastSpeed = new JRadioButton("Fast");
	ButtonGroup myButtons = new ButtonGroup();
	JRadioButton mergeSort = new JRadioButton("Merge");
	JRadioButton insertSort = new JRadioButton("Insertion");
	JRadioButton quickSort = new JRadioButton("Quick");
	ButtonGroup sortButtons = new ButtonGroup();
	
	JTextArea area;
	Font font = new Font("Verdana", Font.BOLD, 16);
	JPanel buttonPanel;
	JTextField field;
	
	boolean stopFlag=true;
	int progSpeed = 250;
	
	JLabel sortLabel = new JLabel("Type of Sort");
	JTextField sortText = new JTextField("");
	
	Thread hrunner;

	MyPanel panel = new MyPanel();
	
	public SortFrame()
	{
		this.myList = new int[MAX_SIZE];
		this.count = 0;
		
		for(int i = 0; i < MAX_SIZE; i++)
		{
			int temp = generator.nextInt(MAX_SIZE);
			this.myList[i] = temp;
			for(int k = 0; k < MAX_SIZE; k++)
			{
				if(this.myList[i] == this.myList[k])
				{
					temp = generator.nextInt(MAX_SIZE);
					this.myList[i] = temp; 
				}
			}
			this.count++;
		}
		
		this.setTitle("Sort Frame");
	    this.getContentPane().setLayout(new FlowLayout());
	    this.getContentPane().add(start); 
	    this.getContentPane().add(stop); 
	    this.getContentPane().add(reset); 

	    this.myButtons.add(slowSpeed);
	    this.myButtons.add(midSpeed);
	    this.myButtons.add(fastSpeed);

	    this.getContentPane().add(slowSpeed);
	    this.getContentPane().add(midSpeed);   
	    this.getContentPane().add(fastSpeed);
	    
	    this.sortButtons.add(mergeSort);
	    this.sortButtons.add(insertSort);
	    this.sortButtons.add(quickSort);
	    
	    this.getContentPane().add(mergeSort);
	    this.getContentPane().add(insertSort);   
	    this.getContentPane().add(quickSort);
	    
	    this.getContentPane().add(panel);
	    
	    
	    start.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(hrunner != null)
	    			{
	    				stopFlag = true;
	    			}
	    		resetArray();
	    		if(insertSort.isSelected())
	    			{
	    				hrunner = new Thread(new Runnable () {
	    					public void run()
	    					{
	    						stopFlag = false;
	    						insertionSort();
	    					} // end of hrunner
	    				});// end of hrunner create
	    				hrunner.start(); // add thread to process queue
	    			}
	    		else if(mergeSort.isSelected())
	    			{
	    			hrunner = new Thread(new Runnable () {
	    				public void run()
	    				{
	    					stopFlag = false;
	    					mergeSort(myList, 0, MAX_SIZE-1);
	    				} // end of run
	    			});// end of hrunner create
	    			hrunner.start(); // add thread to process queue
	    			}
	    		else
	    			{
		    			hrunner = new Thread(new Runnable () {
		    				public void run()
		    			{
		    				stopFlag = false;
		    				quickSort(myList, 0, MAX_SIZE-1);
		    			} // end of run
		    		});// end of hrunner create
		    		hrunner.start(); // add thread to process queue
	    			}
	    		}});
	    
	    stop.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		stopFlag = true;
	    	}});
	    reset.addActionListener(new ActionListener () {
	    	public void actionPerformed(ActionEvent e) {
	    		stopFlag = true;
	    		resetArray();
	    		try{ 
	    		    Thread.sleep(progSpeed);  
	    		    // make current thread running sleep so we can listen to
	    		    // other buttons and see the towers after a pa  
	    		} catch(InterruptedException e1) { System.out.println("Error in thread sleep " + e1);
	    		}
	    			repaint();
	    	}});
	    progSpeed = 500; // the default speed
	}//end of sortFrame
	
	
	//pre: takes in a list and two integer values
	//post: Sorts the list
	public void mergeSort(int myList[], int low, int high)
	{ // mergesorts myList [low:high]
		
		
		int mid;

		if (low < high)  // are there at least 2 values to sort?
		  {
		   mid = (low+high)/2;
		   mergeSort(myList, low, mid);    // sort lower half
		   mergeSort(myList, mid+1, high);  // sort upper half
		   merge(myList, low,mid,mid+1,high);  // merge 2 halves
		  }
		
		repaint();
		
		// now check speed selected again and reset 
		if(midSpeed.isSelected())
		    progSpeed=100;
		else if(fastSpeed.isSelected())
		    progSpeed = 250;
		else
			progSpeed = 500;

		if(stopFlag == false)
		{
		try{ 
		    Thread.sleep(progSpeed); 
		    // make current thread running sleep so we can listen to
		    // other buttons and see the towers after a paint
		} catch(InterruptedException e) { System.out.println("Error in thread sleep " + e);
		}
		}
		
		}//end of mergeSort

		 public void merge( int myList[],int low, int mid, int low1,int high) {
		  // merges two sort		repaint();ed lists within myList,
		  //    myList[low:mid] and myList[low1:high]

		  int temp[] = new int [(mid-low+1)+(high-low1+1)];
		     // temp to hold list as merged.
		  int s1, s2, d, k;  // indexes used to keep track of positions in merging
		  s1=low;            // start of lower half, s1, upper half start is s2
		  s2=low1;
		  d=0;
		  while(s1<=mid && s2<=high)  // while elements in BOTH sublists left
		  {
		    if(myList[s1] < myList[s2])
		      temp[d++] = myList[s1++];
		    else
		      temp[d++] = myList[s2++];
		  }
		  while (s1<=mid)       // while lower half is not merged, copy rest of it
		  {  temp[d++]=myList[s1++];

		  }
		  while (s2<=high)      // while upper half is not merged copy rest into temp
		  {  temp[d++]=myList[s2++];
		  }

		  for(k=0,s1=low;s1<=high; s1++,k++)  // now copy temp BACK to myList
		    myList[s1]=temp[k];
		  
		 }   

		 public int partition ( int list[], int lo, int hi)
		 /* given:  list, the array to be partitioned
		            lo and hi, the index limits of the list to partition
		            pivot - location at which partition takes place.
		    task:  partition the array between lo and hi, returning location
		           of the partition in variable pivot.

		    returns:  list partitioned and pivot as location of partition */
		 {
		   int  pivotvalue;  // type of element being sorted


		   pivotvalue = list[lo];  
		  
		   while( lo < hi)
		   {
		      while (lo < hi && pivotvalue < list[hi]) 
		        hi--;
		      if (lo != hi)
		       {
		        list[lo]=list[hi];
		        lo++;
		       }
		      while (lo < hi && pivotvalue > list[lo] )
		        lo++;
		      if (lo !=hi)
		       {
		        list[hi]= list[lo];
		        hi--;
		        }
		      
		    }  // end of while 
		  list[hi]=pivotvalue;
		  return hi;  // index of partition
		 }

		//pre: takes in a list and two integer values
		//post: Sorts the list
		 public void quickSort(int [] list, int lower, int upper)
		 {
				repaint();
		  int pivotlocation ;

		   if (lower < upper)  //  is a list of more than 1 to sort?
		    {
		     pivotlocation=partition(list, lower,upper);
		     quickSort(list, lower, pivotlocation-1);
		     quickSort(list, pivotlocation+1, upper);
		    }    
		   
		   repaint();
		   
			// now check speed selected again and reset 
		   if(midSpeed.isSelected())
			    progSpeed=100;
			else if(fastSpeed.isSelected())
			    progSpeed = 250;
			else
				progSpeed = 500;

			if(stopFlag == false)
			{
			try{ 
			    Thread.sleep(progSpeed); 
			    // make current thread running sleep so we can listen to
			    // other buttons and see the towers after a paint
			} catch(InterruptedException e) { System.out.println("Error in thread sleep " + e);
			}
			}
			
		   
		  }  // end of  quickSort
	
	//pre: Nothing
	//post: Sorts the list
	public void insertionSort() {
		for(int i = 1; i < this.myList.length; i++)
		{
			int k = i;
			while (k > 0)
			{
				if(myList[k] < myList[k-1])
				{
					int temp = myList[k];
					myList[k] = myList[k-1];
					myList[k-1] = temp;
					k--;
				}
			}
		}
		repaint();
		
		// now check speed selected again and reset 
		if(midSpeed.isSelected())
		    progSpeed=100;
		else if(fastSpeed.isSelected())
		    progSpeed = 250;
		else
			progSpeed = 500;

		if(stopFlag == false)
		{
		try{ 
		    Thread.sleep(progSpeed); 
		    // make current thread running sleep so we can listen to
		    // other buttons and see the towers after a paint
		} catch(InterruptedException e) { System.out.println("Error in thread sleep " + e);
		}
		}
		
		
	}//end of insertionSort
	  
	//pre: nothing
	//post: Resets the array
	public void resetArray()
	{
		this.count = 0;
		
		for(int i = 0; i < MAX_SIZE; i++)
		{
			int temp = generator.nextInt(MAX_SIZE);
			this.myList[i] = temp;
			for(int k = 0; k < MAX_SIZE; k++)
			{
				if(this.myList[i] == this.myList[k])
				{
					temp = generator.nextInt(MAX_SIZE);
					this.myList[i] = temp;
				}
			}
			this.count++;
		}
		repaint();
		
		if(stopFlag == false)
		{
		try{ 
		    Thread.sleep(progSpeed); 
		    repaint();
		    // make current thread running sleep so we can listen to
		    // other buttons and see the towers after a paint
		} catch(InterruptedException e) { System.out.println("Error in thread sleep " + e);
		}
		}
	}//end of resetArray
	
    private static void createAndShowGUI() 
    {
        try {
	    UIManager.setLookAndFeel(
	    		UIManager.getSystemLookAndFeelClassName());
      	 } 
        catch (UnsupportedLookAndFeelException e) {
	    JFrame.setDefaultLookAndFeelDecorated(true);    
        }
        catch (ClassNotFoundException e) {
        	JFrame.setDefaultLookAndFeelDecorated(true);    
        	}
        catch (InstantiationException e) {
        	JFrame.setDefaultLookAndFeelDecorated(true);    
			}
        catch (IllegalAccessException e) {
        }
	 
        SortFrame frame = new SortFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }//end of createAndShowGUI
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    createAndShowGUI();
		}
	    });
    }//end of main
 
    
  public class MyPanel extends JPanel
  {
      MyPanel() {
	  this.setPreferredSize(new Dimension(650,600));
      }

      public void paintComponent(Graphics g)
      { 
	  super.paintComponent(g); // always paint the parent!
	  g.setColor(Color.white);// change to white
	  g.fillRect(0,5,550,550);  // draw a rectangle, 550 by 550 at (5,5)
	  g.setColor(Color.black); // change color to black
	  drawLab9(g, myList); // draw the array on the panel
      }
  
      public void drawLab9(Graphics g, int myList[])
      {
    	for(int i = 0; i < myList.length; i++)
    	{
    		g.drawLine(i, 500-myList[i], i, 600);
    	}
      }
  }//end of MyPanel   

}//end of class
