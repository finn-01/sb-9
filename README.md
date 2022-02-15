


> Written with [StackEdit](https://stackedit.io/).
### **Thymeleaf**

**Thymeleaf** là một Java Template Engine. Có nhiệm vụ xử lý và generate ra các file HTML, XML, v.v..

Các file HMTL do Thymeleaf tạo ra là nhờ kết hợp **dữ liệu** và **template + quy tắc** để sinh ra một file HTML chứa đầy đủ thông tin.
![enter image description here](https://super-static-assets.s3.amazonaws.com/8a72ee8e-d4aa-4a06-985f-e92802c5bc44/images/906f6986-2bfe-4693-8f9c-cd5c8384b5d8.png?w=1500&f=webp)
Việc của bạn là cung cấp dữ liệu và quy định **template** như nào, còn việc dùng các thông tin đó để render ra HTML sẽ do **Thymeleaf** giải quyết.

### **Cú pháp**

Cú pháp của **Thymeleaf** sẽ là một **attributes** (Thuộc tính) của thẻ HTML và bắt đầu bằng chữ `th:`.

Với cách tiếp cận này, bạn sẽ chỉ cần sử dụng các thẻ HTML cơ bản đã biết mà không cần bổ sung thêm syntax hay thẻ mới như JSP truyền thống.

Ví dụ:

Để truyền dữ liệu từ biến `name` trong Java vào một thẻ `H1` của HTML.

```html
<h1 th:text="${name}"></h1>
```

Chúng ta viết thẻ H1 như bình thường, nhưng không chứa bất cứ text nào trong thẻ. Mà sử dụng cú pháp `th:text="${name}"` để **Thymeleaf** lấy thông tin từ biến `name` và đưa vào thẻ `H1`.

Kết quả khi render ra:

```html
// Giả sử String name = "loda"
<h1>Loda</h1>
```

thuộc tính `th:text` biến mất và giá trị biến `name` được đưa vào trong thẻ `H1`.

Đó là cách **Thymeleaf** hoạt động.

### **Model & View Trong Spring Boot**

Trong bài trước, tôi đã demo cách sử dụng đối tượng `Model`. Bây giờ tôi sẽ nói kĩ hơn một chút.

`Model` là đối tượng lưu giữ thông tin và được sử dụng bởi **Template Engine** để generate ra webpage. Có thể hiểu nó là `Context` của **Thymeleaf**

`Model` lưu giữ thông tin dưới dạng key-value.

Trong template thymeleaf, để lấy các thông tin trong `Model`. bạn sẽ sử dụng `Thymeleaf Standard Expression`.

1.  `${...}`: Giá trị của một biến.
2.  `{...}`: Giá trị của một biến được chỉ định (sẽ giải thích ở dưới)

Ngoài ra, để lấy thông tin đặc biệt hơn:

1.  `#{...}`: Lấy message
2.  `@{...}`: Lấy đường dẫn URL dựa theo context của server

Nói tới đây có lẽ hơi khó hiểu, chúng ta sẽ dùng ví dụ để hiểu rõ từng loại Expression.

### `**${...}**` **- Variables Expressions**

Trên Controller bạn đưa vào một số giá trị:

```java
model.addAttribute("today", "Monday");
```

Để lấy giá trị của biến `today`, tôi sử dụng `${...}`

```html
<p>Today is: <spanth:text="${today}"></span>.</p>
```

Đoạn expression trên tương đương với:

```java
ctx.getVariable("today");
```

### `*``**{...}**` **- Variables Expressions on selections**

Dấu `*` còn gọi là `asterisk syntax`. Chức năng của nó giống với `${...}` là lấy giá trị của một biến.

Điểm khác biệt là nó sẽ lấy ra giá trị của một biến cho trước bởi `th:object`

```html
  <divth:object="${session.user}"><!-- th:object tồn tại trong phạm vi của thẻ div này -->

    <!-- Lấy ra tên của đối tượng session.user -->
    <p>Name: <spanth:text="*{firstName}"></span>.</p><!-- Lấy ra lastName của đối tượng session.user -->
    <p>Surname: <spanth:text="*{lastName}"></span>.</p></div>
```

Còn `${...}` sẽ lấy ra giá trị cục bộ trong `Context` hay `Model`.

Vậy đoạn code ở trên tương đương với:

```html
<div><p>Name: <spanth:text="${session.user.firstName}"></span>.</p><p>Surname: <spanth:text="${session.user.lastName}"></span>.</p></div>
```

### `**#{...}**` **- Message Expression**

Ví dụ, trong file config `.properties` của tôi có một message chào người dùng bằng nhiều ngôn ngữ.

```makefile
home.welcome=¡Bienvenido a nuestra tienda de comestibles!
```

Thì cách lấy nó ra nhanh nhất là:

```html
<p th:utext="#{home.welcome}">Xin chào các bạn!</p>
```

Đoạn text tiếng việt bên trong thẻ `p` sẽ bị thay thế bởi thymeleaf khi render `#{home.welcome}`.

### `**@{...}**` **- URL Expression**

`@{...}` xử lý và trả ra giá trị URL theo context của máy chủ cho chúng ta.

Ví dụ:

```
<!-- tương đương với 'http://localhost:8080/order/details?orderId=3' -->
<ahref="details.html"th:href="@{http://localhost:8080/order/details(orderId=${o.id})}">view</a><!-- tương đương  '/order/details?orderId=3' -->
<ahref="details.html"th:href="@{/order/details(orderId=${o.id})}">view</a><!-- tương dương '/gtvg/order/3/details' -->
<ahref="details.html"th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a>

```

Nếu bắt dầu bằng dấu `/` thì nó sẽ là Relative URL và sẽ tương ứng theo context của máy chủ của bạn.
