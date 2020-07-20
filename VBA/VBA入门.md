# 1 常用快捷键
* <Alt+F11>：打开VBE窗口
* \<F5>：在VBE界面调用子过程
* <Alt+F8>：在工作表界面调用模块子过程
* \<F8>:查看代码执行流程

# 2 Excel的对象
## 2.1 常见对象及其含义
| 对象类别 | 含义 |
| :--: | :--: |
| Application | 代表整个Excel应用程序 |
| Workbook | 代表Excel工作簿对象 |
| Worksheet | 代表工作表对象 |
| Window | 代表窗口对象 |
| Range | 代表单元格对象 |
| Shape | 代表嵌入到工作表中的图形对象 |
| Name | 代表名称对象 |
| Chart | 代表图标对象 |
| WorksheetFunction | 代表工作表函数对象 |
| Comment | 代表单元格中的批注对象 |

## 2.2 对象集合
* Workbooks：工作簿集合
* Worksheets:工作表集合
* Comments：批注集合
* Cells：单元格集合
* Shapes：图形对象集合

## 2.3 父对象与子对象
**对象的层次关系：**   
> Application对象 -> Workbook对象 -> Worksheet对象 -> Rows/Columns对象 -> Range对象 -> Comment对象

**访问子对象的语法：对象名称.子对象名称**
> 例：Worksheets(1).Cells(10)

**访问父对象的语法：对象名称.Parent**
> 例：Cells(2).Parent

## 2.4 活动对象
* Activesheet:活动工作表
* ActiveCell:活动单元格
* ActiveChart：活动图表
* ActiveWindow：活动窗口
* ActiveWorkbook：活动工作簿

例：
> Worksheets("sheet3").Activate  
> ActiveSheet.Cells(1) = 123

## 2.5 对象的事件
例：工作簿打开事件
	
	Private Sub Workbook_Open()
		Worksheets(2).Rane("a1") = Date
	End Sub 
注：以上代码必须放在ThisWorkbook窗口中。  

例：工作表改变事件  

	Private Sub Worksheet_Change(ByVal Target As Range)
	    Application.StatusBar = "您当前正在修改" & Target.Address & "的值"
	End Sub

# 3 变量
## 3.1 数据类型
| 数据类型 | 存储空间大小 |
| :--: | :--: |
| Byte | 1字节 |
| Boolean | 2字节 |
| Integer | 2字节 |
| Long | 4字节 |
| Single(单精度浮点型) | 4字节 |
| Double | 8字节 |
| Currency(货币型) | 8字节 |
| Decimal | 14字节 |
| Date | 8字节 |
| String(变长) | 10字节加字符串长度 |
| String(定长) | 字符串长度 |
| Variant(数字) | 16个字节 |
| Variant(字符) | 22个字节加字符串长度 |

## 3.2 声明
> 声明变量有4种方式：Dim, Public, Private, Static

	Dim name
	Dim a as Byte
	Dim a, b, c 
	Dim a As Byte, b, c, d AS String

> 声明常量的语法：   
> [Public|Private] Const constname [As type] = expression

## 3.3 变量的赋值方式与初始值
### 3.3.1 数值型变量
包括Byte、Integer、Long、Single、Double、和Currency，初始值为0。
Let 变量 = 值， 其中Let可以忽略。   
例：a = 125

### 3.3.2 布尔型变量
默认值是False。若对布尔值变量赋初值，那么0当做False处理，0以外都当做True处理。

### 3.3.3 文本型变量
初始值为空文本。

### 3.3.4 日期型变量
赋值范围在0:00:00到23:59:59,初始值是"0:00:00"。对日期型变量赋值必须在前后添加"#"。

	Dim MyDate1 As Date, MyDate2 As Date
	MyDate1 = #5/19/2020#
	MyDate2 = #9:25:48  #

### 3.3.5 数组变量
可以存放无限值，仅受内存限制。

### 3.3.6 对象型变量
赋值语法为 Set 变量 = 对象

	Dim Sht As Worksheet
	Set Sht = Worksheets("Sheet2")

## 3.4 变量的作用域
| 级别 | 作用域 | 存放位置 | 变量的声明方式 |
| :--: | :--: | :--: | :--: |
| 过程级 | 当前过程 | 过程中 | 使用Dim或者Static声明变量 |
| 模块级 | 当前模块 | 模块顶部 | 使用Dim或者Private声明变量 |
| 工程级 | 所有模块 | 模块顶部 | 使用Public声明变量 |

# 4 单元格对象
## 4.1 引用单元格
	Range("A1")
	Cells(1,1)
	Cells(1,"A")
	Range("B2:G10").Cells(2,2)  '表示单元格C3
	Range("B2:G10).Cells(7) '表示单元格B3
	[a5]

## 4.2 引用单元格区域
	Range("A1:V10")
	Range(Range("A1"), Range("V10"))
	[A1:V10]

## 4.3 引用多区域单元格
	Range("D3:F4,G10")
	[D3:F4,G10]

## 4.4 引用整行、整列单元格
	Range("2:2")
	Range("D:A")
	Rows("2")
	Rows("2:4")
	Columns("2")
	Columns("B:B")

## 4.5 按条件引用区域
Range.SpecialCells方法可以返回与指定类型和值相匹配的区域，它的具体参数为：Range.SpecialCells(Type, Value)。

| XlCellType常量 | 含义 | 值 |
| :--: | :--: | :--: |
| xlCellTypeAllFormatConditions | 包含条件格式的单元格 | -4172 |
| xlCellTypeAllValidation |包含有效性验证条件的单元格 |-4174|
| xlCellTypeBlanks | 空单元格 | 4 |
| xlCellTypeComments | 含有注释的单元格 | -4144 |
| xlCellTypeConstants | 含有常量的单元格 | 2 |
| xlCellTypeFormulas | 含有公式的单元格 | -4123 |
| xlCellTypeLastCell | 已用区域中的最后一个单元格 | 11 |
| xlCellTypeSameFormatConditions | 含有相同条件格式的单元格 | -4173 |
| xlCellTypeSameValidation | 含有相同有效性条件的单元格 | -4175 |
| xlCellTypeVisible | 所有可见单元格 | 12 |


| xlSpecialCellsValue常量 | 含义 | 值 |
| :--: | :--: | :--: |
| xlNumber | 数值 | 1 |
| xlTextValues | 文本 | 2 |
| xlLogical | 逻辑值 | 4 |
| xlErrors | 错误值 | 16 |

## 4.6 Resize:重置区域大小
语法：Range.Resize(RowSize, ColumnSize)

	With Worksheets(1).UsedRange
        Worksheets(2).Range("a1").Resize(.Rows.Count, .Columns.Count).Value = .Value
    End With

## 4.6 Offset:根据偏移量引用新区域
语法：Range.Offset(RowOffset, ColumnOffset)

[a1].Offset(2,3) 'D3单元格
Range("D2").Offset(,4) 'H2单元格
Range("D2:C3").Offset(1,1) 'D3:E4

## 4.7 Union:多区域合集
	Union(Range("A2:B2"), Range("D3:G4"))

## 4.8 Intersect:单元格、区域的交集
	Application.Intersect(Range("a1:a10"), Range("2:2"))

## 4.9 End:引用源区域的区域尾端的单元格
语法为：Range.End(Direction)

**End属性参数**

| 名称 | 值 | 描述 |
| :--: | :--: | :--: |
| xlDown | -4121 | 向下 |
| xlToLeft | -4159 | 向左 |
| xlToRight | -4161 | 向右 |
| xlUp| -4162 | 向下 |

# 5 常用语法
## 5.1 创建输入框
基本语法：Application.InputBox(prompt, Title, Default, Left, Top, HelpFile, HelpContextID, Type)

**参数详解**

| 名称 | 是否必选 | 描述 |
| :--: | :--: | :--: |
| Prompt | 必选 | 要在对话框中显示的消息 |
| Title | 可选 | 输入框标题，默认为“input” |
| Default | 可选 | 文本框初始值 |
| Left | 可选 | 对话框相对于屏幕左上角的X坐标 |
| Top | 可选 | 对话框相对于屏幕左上角的Y坐标 |
| HelpFile | 可选 | 此输入框使用的帮助文件名 |
| HelpContextID | 可选 | HelpFile中帮助主题的上下文ID号 |
| Type | 可选 | 返回的数据类型，默认返回文本 |

**Type参数可选值**

| 值 | 含义 |
| :--: | :--: |
| 0 | 公式 |
| 1 | 数字 |
| 2 | 文本（字符串）|
| 4 | 逻辑值（True或False）|
| 8 | 单元格引用，作为一个Range对象 |
| 16 | 错误值 |
| 64 | 数值数组 |

## 5.2 条件判断语句
### 5.2.1 IIF函数的语法
基本语法为：IIF(expr, truepart, falsepart)

	Range("B1") = IIf(Range("a1") >= 60, "及格", "不及格")

### 5.2.2 IF语法 
 
	IF condition Then 
		[statements]
	[ElseIF condition-n Then
		[elseifstatements] ...
	[Else
		[elsestatements]]
	End IF

### 5.2.3 Select Case语法

	Select Case testexpression
	[Case expressionlist-n
		[statements-n]]...
	[Case Else
		[elsestatements]]
	End Select

### 5.2.4 choose语法

	choose(index, choice-1[, choice-2, ... [, choice-n]])

## 5.3 循环语句
### 5.3.1 For Next语句

	For counter = start To end [Step step]
		[statements]
		[Exit For]
		[statements]
	Next [counter]

### 5.3.2 For Each Next语句
	
	For Each element In Group
		[statements]
		[Exit For]
		[statements]
	Next [element]

### 5.3.3 Do Loop 语句

	'形式一:一直循环代码
	Do
		[statements]
		[Exit Do]
		[statements]
	Loop
	
	'形式二：只要符合某条件，那么一直循环代码
	Do While condition
		[statements]
		[Exit Do]
		[statements]
	Loop
	
	'形式三：只要不符合条件，那么一直循环代码
	Do Until condition
		[statements]
		[Exit Do]
		[statements]
	Loop
	
	'形式四：一直循环代码，直到符合条件时停止
	Do
		[statements]
		[Exit Do]
		[statements]
	LoopWhile condition
	
	'形式五：一直循环代码，直到不符合条件时停止
	Do
		[statements]
		[Exit Do]
		[statements]
	Loop Until condition

## 5.4 选择文件与文件夹
### 5.4.1 FileDialog对象
* FileDialog.Show方法
> 用于显示FileDialog对象创建的对话框，没有参数，有一个返回值。单击“打开”按钮时其返回值为-1，单击“取消”按钮时返回值为0。

* FileDialog.AllowMultiSelect属性
> 代表是否允许多选。赋值为True表示允许多选，赋值为False表示只能单选。

* FileDialog.SelectedItems属性
> 代表用户所选择对象的集合，对象的类型由对话框类型而定。

FileDialog对象可以创建4种对话框，对话框类型由参数决定，具体语法：

	Application.FileDialog(fileDialogType)

**MsoFileDialogType常量**

| 名称 | 值 | 说明 |
| :--: | :--: | :--: |
| msoFileDialogOpen | 1 | “打开”对话框 |
| msoFileDialogSaveAs | 2 | “另存为”对话框 |
| msoFileDialogFilePicker | 3 | “文件选取器”对话框 |
| msoFileDialogFolderPicker | 4 | “文件夹选取器”对话框 |

### 5.4.2 选择路径

	Sub 浏览并获取指定路径名称()
	    Dim pathSht As String
	    With Application.FileDialog(msoFileDialogFolderPicker)
	        If .Show = -1 Then
	            pathSht = .SelectedItems(1)
	        Else
	            Exit Sub
	        End If
	    End With
	    
	    pathSht = pathSht & IIf(Right(pathSht, 1) = " \ ", "", " \ ")
	    MsgBox pathSht
	End Sub

### 5.4.3 选择文件

	Sub 浏览并报告所有文件名称()
	    With Application.FileDialog(msoFileDialogFilePicker)
	        If .Show = -1 Then
	            For Item = 1 To .SelectedItems.Count
	                MsgBox .SelectedItems(Item)
	            Next
	        Else
	            Exit Sub
	        End If
	    End With
	End Sub

### 5.4.4 按类型选择文件
	Application.GetOpenFilename(FileFilter, FilterIndex, Title, ButtonText, MultiSelect)

**GetOpenFilename方法参数说明**

| 参数名称 | 功能描述 |
| :--: | :--: |
| FileFilter | 用于指定文件筛选条件的字符串 |
| FilterIndex | 指定默认文件筛选条件的索引号，取值范围为1到由FileFilter所指定的筛选条件数目 |
| Title | 指定对话框标题，默认为“打开” |
| ButtonText | 仅限Macintosh |
| MultiSelect | 赋值为True则允许选择多个文件名 |

	Sub 批量导入图片文件名称()
	    On Error Resume Next
	    Dim fileName, i As Integer
	    fileName = Application.GetOpenFilename("图片文件,*.jpg;*.png", , "请选择图片文件", , True)
	    If Err.Number > 0 Then
	        Exit Sub
	    End If
	    For i = 1 To UBound(fileName)
	        Cells(i, 1) = fileName(i)
	    Next i
	End Sub 

# 6 事件
## 6.1 事件的禁用与启用
当Application对象的EnableEvents属性被赋值为True时表示启用事件，赋值为False则禁用事件。

	Application.EnableEvents = True
	Application.EnableEvents = False

## 6.2 事件的特例
* 删除空白单元格时触发Worksheet_Change事件
* 插入批注时不触发任何事件
* 修改单元格格式不触发事件
* 清除格式会触发Worksheet_Change事件
* 数据分列时不触发事件
* 表单控件修改单元格不触发事件