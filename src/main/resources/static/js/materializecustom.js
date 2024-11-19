/*
 * ====================================================================
 *  TLDR
 *   - add search bar to materialize formSelect
 *   - options line 41 - 44
 * --------------------------------------------------------------------
 *  MaterializeCSS tweak to allow text filtering in multiple selects.
 *  - No need to modify Materialize JS files
 *  - Grabs keyboard focus nicely
 *  - No need for annoying JQuery
 *  - Renders correctly for any widget width
 *  - handles optgroups -> only hide empty optgroups,
 *                         show optgroup if child is visible
 *  - handle keyboard navigation - ArrowUp + ArrowDown events
 *  - supports multi select
 *  - hooks M.FormSelect.init
 *  - add M.SelectSearch.init(els) for manual initialisation
 *  - do own autoload
 *    - could be disabled with option 'doOwnAutoload' (line 42)
 *  - sticky searchbar
 *    - disable by option 'stickySearchbar' (line 43)
 *  - scroll focused element into view (keyboard navigation)
 *    - disable by option 'scrollIntoView' (line 44)
 * --------------------------------------------------------------------
 *  Installation
 *  - create js file, and load this after materialize.js
 *  - place attribute to your select:
 *     searchable="placeholder"
 *  - add css rule after materialize.css:
 *     .select-dropdown.dropdown-content li.hover {
 *       background-color: rgba(0,0,0,0.08); }
 * --------------------------------------------------------------------
 * Links
 * - https://github.com/Dogfalo/materialize/issues/3096#issuecomment-778513315
 * - https://jsfiddle.net/cherrg/vgn3Law4/
 * ====================================================================
*/
(function(){
	"use strict";
	// script options ---------------------------------
	const keyBoardOnMobile = true;
	const doOwnAutoload = true;
	const stickySearchbar = true;
	const scrollIntoView = true;

	// helper functions -------------------------------
	const hasClass = function(el, className){
		if (el.classList) return el.classList.contains(className);
		return (new RegExp('( |^)' + className + '( |$)')).test(className);
	};
	const addClass = function(el, className){
		if (el.classList) el.classList.add(className)
		else if (!hasClass(el, className)) el.className += " " + className;
	}
	const removeClass = function(el, className){
		if (el.classList) el.classList.remove(className)
		else if (hasClass(el, className)) el.className = el.className.replace(new RegExp('( |^)' + className + '( |$)'), ' ');
	}
	const scrollIntoViewIfNeeded = function ( el, strict ) {
		// --------------------
		// init parameters
		// unpack jquery objects
		if (typeof jQuery === "function" && el instanceof jQuery) {
			el = el[0];
		}
		if (typeof strict !== 'boolean') {
			strict = true;
		}
		let parent = el.parentNode;
		let parent_rect = parent.getBoundingClientRect();
		let target_rect = el.getBoundingClientRect();
		if (stickySearchbar) {
			// as searchbar is first element, reduce parent height and top by search bar size
			let searchbar_rect = parent.firstElementChild.getBoundingClientRect();
			parent_rect = {
				top: parent_rect.top + searchbar_rect.height,
				height: parent_rect.height - searchbar_rect.height,
				left: parent_rect.left,
				width: parent_rect.width
			};
		}
		// --------------------
		// scroll into view
		if (target_rect.top < parent_rect.top && (strict || target_rect.top + target_rect.height < parent_rect.top)){
			// element out of bounds -> top
			// as search bar is forst element
			let diff = parent_rect.top - target_rect.top;
			parent.scrollBy(0, -diff);
		} else if (target_rect.top + target_rect.height > parent_rect.top + parent_rect.height && (strict || target_rect.top > parent_rect.top + parent_rect.height)) {
			let diff = parent_rect.top + parent_rect.height - (target_rect.top + target_rect.height);
			parent.scrollBy(0, -diff);
		}
	}

	const init = function (els, force_init) {
		if (typeof force_init !== "boolean") {
			force_init = false;
		}
		if (typeof jQuery === "function" && els instanceof jQuery) {
			els = els.toArray();
		} else if (typeof els === 'object' && !els.hasOwnProperty('forEach') && typeof els.forEach !== 'function') {
			els = [els];
		}

		if (typeof els === 'undefined'|| els === null) return;

		els.forEach(elem => {
			if (!force_init && !elem.hasAttribute('searchable')) {
				// filter for searchable attribute
				return;
			}

			if (elem.dataset.hasOwnProperty('searchableInitDone')) {
				return;
			} else {
				elem.dataset.searchableInitDone = '1';
			}

			const select = elem.M_FormSelect;
			const options = select.dropdownOptions.querySelectorAll('li');

			// Add search box to dropdown
			const placeholderText = select.el.getAttribute('searchable');
			const searchBox = document.createElement('div');
			searchBox.style.cssText =
				'padding: 6px 16px 0 16px;' +
				(stickySearchbar?
				// search boy sticky on top
				'position: -webkit-sticky;' +
				'position: sticky;' +
				'top: 0;' +
				'z-index: 1;' +
				'background-color: inherit;' : '');
			searchBox.innerHTML = `<input type="text" placeholder="${placeholderText}">`;
			select.dropdownOptions.prepend(searchBox);
			const searchInput = searchBox.firstElementChild;

			// Function to filter dropdown options
			function filterOptions(event) {
				// fix index value -> as materialize key handler changes value after keydown - event
				if (event.code === 'ArrowDown' || event.code === 'ArrowUp'){
					let hover_idx = -1;
					// find current hover index
					for(let i = 0; i < options.length; i++){
						if (hover_idx === -1 && hasClass(options[i], 'hover')){
							hover_idx = i;
							break;
						}
					}
					if (hover_idx > -1) {
						select.dropdown.focusedIndex = hover_idx + 1;
						if (scrollIntoView) {
							scrollIntoViewIfNeeded(options[hover_idx], true);
						}
					}
					return;
				} else if (event.code === 'Enter') {
					return;
				}

				// search for value
				const searchText = searchInput.value.toLowerCase().trim();
				let lastOptgroup = null;
				for(let i = 0; i < options.length; i++){
					const text = options[i].textContent.toLowerCase();
					const display = text.indexOf(searchText) === -1 ? 'none' : 'block';
					if (hasClass(options[i], 'optgroup')) {
						lastOptgroup = display === 'none' ? options[i] : null;
					} else if (!hasClass(options[i], 'optgroup-option')) {
						lastOptgroup = null;
					} else {
						if (display !== 'none' && lastOptgroup !== null) {
							lastOptgroup.style.display = display;
						}
					}
					options[i].style.display = display;
				}
				select.dropdown.recalculateDimensions();
				// recalculate may moves focus away in case of resize
				searchInput.focus();
			}

			// Function to handle ArrowUp/ArrowDown keyboard events
			// has to be hooked on keydown to get repeated key events in case of press&hold
			function filterNavigate(event) {
				// handle keyboard focus events
				if (event.code === 'ArrowDown' || event.code === 'ArrowUp'){
					let hover_idx = -1;		// currently hovered option
					let selected_idx = -1;	// currently selected option - start point for up/down, used if nothing hovered
					let visibles = [];
					// find current hover index
					for(let i = 0; i < options.length; i++){
						let optionIsVisible = window.getComputedStyle(options[i]).display !== 'none';
						if (hover_idx === -1 && hasClass(options[i], 'hover')){
							hover_idx = i;
						}
						if(optionIsVisible){
							visibles.push(i);
							if (hover_idx === -1 && selected_idx === -1 && hasClass(options[i], 'selected')){
								selected_idx = i;
							}
						}
					}
					if (hover_idx === -1 && selected_idx !== -1) {
						hover_idx = selected_idx;
					}
					let new_hover_idx = -1;
					if (visibles.length > 0){
						let direction = event.code === 'ArrowDown' ? 1 : -1;
						if (hover_idx === -1 || visibles.indexOf(hover_idx) === -1) {
							new_hover_idx = visibles[direction === 1 ? 0 : visibles.length - 1];
						} else {
							new_hover_idx = visibles[(visibles.length + visibles.indexOf(hover_idx) + direction) % visibles.length];
						}
					}
					// unset hover
					if (hover_idx > -1) {
						removeClass(options[hover_idx], 'hover');
					}
					if (new_hover_idx > -1) {
						addClass(options[new_hover_idx], 'hover');
						select.dropdown.focusedIndex = new_hover_idx + 1;
					}
				}
			}

			// Function to give keyboard focus to the search input field
			function focusSearchBox() {
				searchInput.focus({
					preventScroll: true
				});
			}

			// Function to init search field and pull focus to search input
			function onSelectDropdownShow() {
				// clear search on open
				if (searchInput.value!=='') {
					searchInput.value='';
					// show all entries
					setTimeout(function(){
						searchBox.dispatchEvent(new KeyboardEvent('keyup', {'key':'Backspace', 'code':'Backspace', 'keyCode':8}));
					}, 200);
				}
				focusSearchBox();
			}

			select.dropdown.options.autoFocus = false;

			if (keyBoardOnMobile || window.matchMedia('(hover: hover) and (pointer: fine)').matches) {
				// listen to Click and ArrowDown select open events
				select.input.addEventListener('click', onSelectDropdownShow);
				select.input.addEventListener('keyup', function(event){
					if (event.code==='ArrowDown') { // select open event
						onSelectDropdownShow();
					}
				});
				for(let i = 0; i < options.length; i++){
					options[i].addEventListener('click', focusSearchBox);
				}
			}

			// filter text
			searchBox.addEventListener('keyup', filterOptions);
			// handle ArrowUp/ArrowDown keyboard events
			searchBox.addEventListener('keydown', filterNavigate);
		});
	}

	// hook materialize select init
	if (typeof M !== 'object') {
		let script_name = (typeof document.currentScript != 'undefined') ? document.currentScript.getAttribute('src') : 'materializecss-select-search.js';
		console.error('['+script_name+'] Has to be loaded AFTER materialize.js and before M.AutoInit() call. To manually init: call M.SelectSearch.init(els);');
	} else {
		M['SelectSearch'] = {
			'init' : function(els) {
				init(els, true);
			}
		};
		const SelectInit = M.FormSelect.init;
		M.FormSelect.init = function (els, options) {
			let t = this;
			SelectInit.call(t, els, options);
			init(els, false);
		}
	}

	// auto init disabled
	if (doOwnAutoload) {
		document.addEventListener('DOMContentLoaded', () => {
			init(document.querySelectorAll('select[searchable]'), true);
		});
	}
})();
