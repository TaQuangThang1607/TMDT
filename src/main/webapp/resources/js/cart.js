(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner(0);


    // Fixed Navbar
    $(window).scroll(function () {
        if ($(window).width() < 992) {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow');
            } else {
                $('.fixed-top').removeClass('shadow');
            }
        } else {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow').css('top', 0);
            } else {
                $('.fixed-top').removeClass('shadow').css('top', 0);
            }
        }
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });


    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 2000,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 1
            },
            992: {
                items: 2
            },
            1200: {
                items: 2
            }
        }
    });


    // vegetable carousel
    $(".vegetable-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 2
            },
            992: {
                items: 3
            },
            1200: {
                items: 4
            }
        }
    });


    // Modal Video
    $(document).ready(function () {
        var $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        console.log($videoSrc);

        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        })
    });


    // Gỡ bỏ sự kiện click cũ trước khi gắn mới
    $('.quantity button').off('click').on('click', function (e) {
        e.preventDefault(); // Ngăn chặn hành vi mặc định
        e.stopPropagation(); // Ngăn chặn sự kiện lan truyền

        const button = $(this);
        const inputGroup = button.closest('.quantity');
        const input = inputGroup.find('input');
        const oldValue = parseInt(input.val());
        let newVal;

        // Xác định thay đổi số lượng
        if (button.hasClass('btn-plus')) {
            newVal = oldValue + 1;
        } else {
            newVal = oldValue > 1 ? oldValue - 1 : 1;
        }

        // Lấy thông tin giá và ID
        const price = parseFloat(input.attr('data-cart-item-price'));
        const cartItemId = input.attr('data-cart-item-id');

        // Gửi yêu cầu AJAX để cập nhật số lượng trên server
        $.ajax({
            url: '/update-cart-quantity/' + cartItemId,
            type: 'POST',
            data: {
                quantity: newVal,
                _csrf: $('input[name="_csrf"]').val()
            },
            success: function (response) {
                // Cập nhật giá trị input trên giao diện
                input.val(newVal);

                // Cập nhật thành tiền của sản phẩm
                const totalElement = $(`td[data-cart-item-total="${cartItemId}"]`);
                if (totalElement.length) {
                    const newTotal = price * newVal;
                    totalElement.text(formatCurrency(newTotal) + ' đ');
                }

                // Cập nhật tổng tiền giỏ hàng
                updateCartTotal();
            },
            error: function (xhr, status, error) {
                console.error('Error updating cart quantity:', error);
                alert('Không thể cập nhật số lượng. Vui lòng thử lại.');
                input.val(oldValue); // Hoàn tác nếu lỗi
            }
        });
    });

    // Hàm định dạng tiền tệ
    function formatCurrency(number) {
        return number.toLocaleString('vi-VN', { minimumFractionDigits: 0 });
    }

    // Hàm tính và cập nhật tổng tiền giỏ hàng
    function updateCartTotal() {
        let newTotal = 0;

        // Duyệt qua tất cả sản phẩm để tính tổng
        $('tbody tr').each(function () {
            const input = $(this).find('.quantity input');
            const price = parseFloat(input.attr('data-cart-item-price'));
            const quantity = parseInt(input.val());
            if (!isNaN(price) && !isNaN(quantity)) {
                newTotal += price * quantity;
            }
        });

        // Cập nhật tổng tiền
        const totalPriceElement = $('td[data-cart-total]');
        if (totalPriceElement.length) {
            totalPriceElement.text(formatCurrency(newTotal) + ' đ');
        }
    }
})

(jQuery);