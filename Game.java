//package GameCode;

import java.awt.event.*;


public abstract class Game extends KeyAdapter {


	protected int[][] board;	//用于记录游戏9个方块中的数字
	boolean running=false;		//用于记录游戏运行状态，true表示正在游戏

	public Game(){	}

	public void Init(){
		//游戏初始化函数，用作开始新一盘游戏
		board=new int[4][4];	//将各位置的方块初始化
		running=true;			//将游戏运行状态设置成游戏中
		NewStep();				//生成第一个随机方块
		show();					//将游戏状态输出并显示
	}


	abstract void End();		//游戏结束后的操作，留于与GUI设计对接的时候实现
	abstract void FrameShow();	//用于显示游戏数据给用户，留于与GUI设计对接的时候实现

	private boolean CheckEnd(){
		//此函数用于检查游戏时候结束，如果游戏结束返回false

		for(int x=0;x<3;x++){

			for(int y=0;y<3;y++){
				if(board[x][y]==board[x][y+1]||board[x][y]==0) 
					return true;
				if(board[x][y]==board[x+1][y]||board[x][y]==0) 
					return true;
			}
			//先检查右下角9个方块有没有与右边或者上面一个相同的，或者不存在方块的

			if(board[3][x]==board[3][x+1]||board[3][x]==0)
				return true;
			//再检查最上一排有没有连续两个相同的，或者不存在方块的

			if(board[x][3]==board[x+1][3]||board[x][3]==0)
				return true;
			//最后检测最右边一列有没有连续两个相同的，或者不存在方块的

		}

		if(board[3][3]==0)return true;
		//此时已经知道全部位置没有连续两个相同的或者不存在方块的情况，最后检查最右上角是否有方块

		//若最右上角也有方块则游戏结束，并将游戏运行状态调为false
		return running=false;
	}

	private void NewStep(){
		//此函数用于在游戏进行时随机生成新的方块

		int x,y;
		do{
			x=(int)(Math.random()*100)%4;
			y=(int)(Math.random()*100)%4;
		}while(board[x][y]!=0);
		//随机找到一个没有方块存在的位置

		board[x][y]=(int)(Math.random()*1000)%1000<800?2:4;
		//在随机抽取的位置中生成一个行的方块，按照7：3的概率随机生成2和4

	}

	private void show(){
		//此函数用于将游戏操作结果输出在控制台，方便调用者调试
		for(int i=0;i<100;i++)System.out.println();
		for(int y=3;y>=0;y--){
			System.out.println("----------------------");
			for(int x=0;x<4;x++){
				System.out.printf("|%4d",board[x][y]);
			}
			System.out.println("|");
		}
		System.out.println("----------------------");

		FrameShow();//调用抽象函数，将游戏实时显示给实际用户
	}


	private boolean down(){

		//第一步检查该按键操作是否能进行，
		boolean enable=false;
		for (int x=0;x<4;x++){
			int t=0;
			for(int y=3;y>=0;y--){
				if(enable) break;
				if(t==0) 
					t=board[x][y];
				else {
					if(board[x][y]==0||board[x][y]==t) 
						enable=true;
					else 
						t=board[x][y];
				}

			}
		}
		if(!enable)return false;


		//将非零方块向下堆积
		for(int x=0;x<4;x++)
			for(int y=2;y>=0;y--)
				if(board[x][y]==0){
					for(int t=y;t<3;t++)
						board[x][t]=board[x][t+1];
					board[x][3]=0;

				}

		//最后，将各列相同的方块相加起来
		for(int x=0;x<4;x++){
			if(board[x][0]==board[x][1]&&board[x][0]!=0){
				board[x][0]*=2;
				board[x][1]=board[x][2];
				board[x][2]=board[x][3];
				board[x][3]=0;
			}
			if(board[x][1]==board[x][2]&&board[x][1]!=0){
				board[x][1]*=2;
				board[x][2]=board[x][3];
				board[x][3]=0;
			}
			else if(board[x][2]==board[x][3]&&board[x][2]!=0){
				board[x][2]*=2;
				board[x][3]=0;
			}
		}

		return true;
	}

	private void turn(int times){
		//此函数作用是将记录矩阵向左旋转90度，参数times代表旋转次数
		for(int i=0;i<times;i++){
			int[][] temp=new int[4][4];
			for(int x=0;x<4;x++)
				for(int y=0;y<4;y++)
					temp[x][y]=board[y][3-x];

			board=temp;
		}


	}


	@Override
	public void keyPressed(KeyEvent e) {
		//此函数用作监听键盘事件，对应执行游戏中向上，向下，向左，向右，4个步骤

		if(!running) return;		//如果游戏状态不是正在游戏中，不执行游戏步骤
		switch(e.getKeyCode()){
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			if(!down())return;//如果监听到向上步骤对应的按键，则执行向上的步骤
			//如果此步骤不能被执行，直接放弃后续步骤
			break;

		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			//向上的步骤相旋转180度后执行向下的步骤后再旋转180度，另外两个方向方法同理
			turn(2);
			if(!down()){
				turn(2);
				return;		
			}
			turn(2);
			break;

		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			turn(1);
			if(!down()){
				turn(3);
				return;		
			}
			turn(3);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			turn(3);
			if(!down()){
				turn(1);
				return;		
			}
			turn(1);
			break;
		default:
			break;
		}
		NewStep();					//随机生成新的方块
		show();						//将此步游戏运行结果输出以及显示
		if(!CheckEnd()){			//检查是否达到游戏结束条件，如果达到，则结束游戏
			System.out.println("Game Over!");
			End();
		}

	}

}
