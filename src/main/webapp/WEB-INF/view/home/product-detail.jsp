```jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product Detail</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <style>
        .reply-form {
            display: none;
        }
        .content-container {
            padding-top: 100px;
        }
        @media (max-width: 768px) {
            .content-container {
                padding-top: 80px;
            }
        }
    </style>
</head>
<body class="bg-gray-100">
    <jsp:include page="layout/header.jsp" />

    <div class="container mx-auto m-6 content-container">
        <!-- Product Information -->
        <div class="bg-white p-4 rounded-lg shadow-md mb-6">
            <div class="flex flex-col lg:flex-row gap-6">
                <div class="lg:w-1/2 xl:w-2/5">
                    <img src="/images/product/${product.imageUrl}" alt="${product.name}" 
                         class="w-full h-auto max-h-[500px] object-contain rounded-lg">
                </div>
                <div class="lg:w-1/2 xl:w-3/5">
                    <h1 class="text-3xl font-bold mb-4">${product.name}</h1>
                    <p class="text-lg mb-2">Price: <span class="font-semibold"><fmt:formatNumber value="${product.price}" pattern="#,##0"/> đ</span></p>
                    <p class="text-lg mb-2">Description: <span>${product.description}</span></p>
                    <p class="text-lg mb-2">Stock: <span class="font-semibold">${product.stock}</span></p>
                    <p class="text-lg mb-2">Sold: <span class="font-semibold">${product.soldQuantity}</span></p>
                    <p class="text-lg mb-2">Category: <span class="font-semibold">${product.categoryName}</span></p>
                    <form action="/add-product-to-cart/${product.id}" method="post" class="mt-4">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                            <i class="fa fa-shopping-bag mr-2"></i> Add to Cart
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Comments Section -->
        <div class="bg-white p-6 rounded-lg shadow-md">
            <h2 class="text-2xl font-bold mb-4">Comments</h2>
            <!-- Comment Form -->
            <form action="/product/${product.id}/comment" method="post" class="mb-6">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <textarea name="content" class="w-full p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" rows="4" placeholder="Write your comment..." required></textarea>
                <button type="submit" class="mt-2 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">Post Comment</button>
            </form>

            <!-- Comment List -->
            <c:forEach var="comment" items="${comments}">
                <div class="mb-4 border-b pb-4">
                    <p class="text-sm text-gray-500">
                        <strong><c:out value="${comment.user.email}"/></strong> - <fmt:formatDate value="${comment.createdAtAsDate}" pattern="dd/MM/yyyy HH:mm"/>
                    </p>
                    <p class="text-gray-800"><c:out value="${comment.content}"/></p>
                    <button onclick="toggleReplyForm('${comment.id}')" class="text-sm text-blue-500 hover:underline mt-1">Reply</button>
                    <!-- Reply Form -->
                    <form action="/product/${product.id}/comment" method="post" class="reply-form mt-2" id="reply-form-${comment.id}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="parentCommentId" value="${comment.id}"/>
                        <p class="text-sm text-gray-500 mb-1">Replying to <strong><c:out value="${comment.user.email}"/></strong></p>
                        <textarea name="content" class="w-full p-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" rows="3" placeholder="Write your reply..." required></textarea>
                        <div class="mt-2 flex space-x-2">
                            <button type="submit" class="bg-green-500 text-white px-4 py-1 rounded hover:bg-green-600">Confirm</button>
                            <button type="button" onclick="toggleReplyForm('${comment.id}')" class="bg-gray-300 text-gray-700 px-4 py-1 rounded hover:bg-gray-400">Cancel</button>
                        </div>
                    </form>
                    <!-- Replies -->
                    <c:forEach var="reply" items="${comment.replies}">
                        <div class="ml-8 mt-2 border-l-2 pl-4">
                            <p class="text-sm text-gray-500">
                                <strong><c:out value="${reply.user.email}"/></strong> - <fmt:formatDate value="${reply.createdAtAsDate}" pattern="dd/MM/yyyy HH:mm"/>
                            </p>
                            <p class="text-gray-800"><c:out value="${reply.content}"/></p>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    </div>

    <jsp:include page="layout/footer.jsp" />

    <!-- JavaScript Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="/lib/easing/easing.min.js"></script>
    <script src="/lib/waypoints/waypoints.min.js"></script>
    <script src="/lib/lightbox/js/lightbox.min.js"></script>
    <script src="/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="/js/main.js"></script>
    <script>
        function toggleReplyForm(commentId) {
            const form = document.getElementById('reply-form-' + commentId);
            if (form) {
                form.style.display = form.style.display === 'none' ? 'block' : 'none';
            } else {
                console.error('Reply form not found for comment ID: ' + commentId);
            }
        }

        // Validate comment form before submission
        $('form').on('submit', function(e) {
            const content = $(this).find('textarea[name="content"]').val().trim();
            if (!content) {
                e.preventDefault();
                alert('Comment cannot be empty!');
            }
        });
    </script>
</body>
</html>
```